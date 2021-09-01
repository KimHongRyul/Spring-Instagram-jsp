package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진() {
		return imageRepository.mPopular();
	}
	
	
	@Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지를 해서 더티체킹, flush(반영) X
	public Page<Image> 이미지스토리(int principalId,Pageable pageable) {
		Page<Image> images = imageRepository.mStory(principalId,pageable);
		
		// 2(coss) 로그인 
		// image에 좋아요 상태 담기
		images.forEach((image)->{
			image.setLikeCount(image.getLikes().size());
			image.getLikes().forEach((like)->{
				if(like.getUser().getId() == principalId) { 
					// 해당 이미지에 좋아요한 사람들 찾아서 현재 로그인한 사람이 좋아요 한 것인지 비교
					image.setLikeState(true);
				}
			});
		});
		return images;
	}
	
	@Value("${file.path}")
	private String uploadFolder;
	
	public void 사진업로드(ImageUploadDto imageUploadDto,PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID(); // 
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); // 1.jpg
		System.out.println("이미지 파일 이름 : " + imageFileName);
		
		// 실제 저장되는 경로
		Path imageFilePath = Paths.get(uploadFolder+imageFileName); // 저장 경로 + 파일명
		
		// 트라이 캐치를 거는 이유 : 통신, I/O -> 예외가 발생 할 수 있다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());  // path, 실제 이미지 파일,
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());
		imageRepository.save(image);
		
	}
	
}
