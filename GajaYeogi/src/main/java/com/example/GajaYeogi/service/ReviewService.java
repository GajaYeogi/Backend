package com.example.GajaYeogi.service;

import com.example.GajaYeogi.dto.PostDto;
import com.example.GajaYeogi.dto.ReviewDto;
import com.example.GajaYeogi.entity.*;
import com.example.GajaYeogi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final ReviewWriteidRepository writeidRepository;
    private final VisitidRepository visitidRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ScrapRepository scrapRepository, ReviewWriteidRepository writeidRepository,
                         UserRepository userRepository, VisitidRepository visitidRepository){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.scrapRepository = scrapRepository;
        this.writeidRepository = writeidRepository;
        this.visitidRepository = visitidRepository;
    }

    //게시글 작성
    public String Reviewwrite(ReviewDto reviewDto){
        try{
            ReviewEntity reviewEntity = new ReviewEntity();
            reviewEntity.setOriginalpostid(reviewDto.getOriginalpostid());
            reviewEntity.setReviewuser(reviewDto.getReviewuser());
            reviewEntity.setReviewusername(reviewDto.getReviewusername());
            reviewEntity.setReviewcontent(reviewDto.getReviewcontent());
            reviewEntity.setVisitcount(0L);

            List<ReviewImgEntity> imageEntities = saveImages(reviewDto.getReviewimg(), reviewEntity);
            reviewEntity.setReviewimage(imageEntities);

            reviewRepository.save(reviewEntity);

            Optional<UserEntity> userOptional = userRepository.findByUser(reviewDto.getReviewuser());

            if (userOptional.isPresent()) {
                Optional<ReviewWriteidEntity> writeidOptional = writeidRepository.findByReviewwriteid(String.valueOf(reviewEntity.getReviewid()));
                if (writeidOptional.isPresent()) {
                    return ("이미 작성중이 완료된 게시물입니다.");
                } else {
                    UserEntity newuser = userOptional.get();
                    ReviewWriteidEntity writeid = new ReviewWriteidEntity();
                    writeid.setReviewwriteid(String.valueOf(reviewEntity.getReviewid()));
                    writeid.setUserentity(newuser);

                    writeidRepository.save(writeid);
                }
            }else{
                return("유저가 존재하지 않습니다.");
            }

            return "글 작성 성공!";
        }catch(Exception e){
            e.printStackTrace();
            return "505 예기치 못한 오류입니다";
        }
    }

    //게시글 전부 조회
    public List<ReviewDto> getAllReview(){
        List<ReviewDto> reviewlist = new ArrayList<>();

        try{
            List<ReviewEntity> reviewEnitites = reviewRepository.findAll();
            for(ReviewEntity entity : reviewEnitites){
                ReviewDto reviewDto = new ReviewDto();
                reviewDto.setReviewid(String.valueOf(entity.getReviewid()));
                reviewDto.setOriginalpostid(entity.getOriginalpostid());
                reviewDto.setReviewuser(entity.getReviewuser());
                reviewDto.setReviewusername(entity.getReviewusername());
                reviewDto.setReviewcontent(entity.getReviewcontent());

                List<String> reviewimgurls = new ArrayList<>();
                for(int i = 0; i < entity.getReviewimage().size(); i++){
                    String reviewimgurl = entity.getReviewimage().get(i).getReviewimgpath();
                    reviewimgurls.add(reviewimgurl);
                }
                reviewDto.setReviewimgurl(reviewimgurls);

                reviewlist.add(reviewDto);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return reviewlist;
    }

    //특정 게시글 조회
    public List<ReviewDto> getReview(ReviewDto reviewDto){
        List<ReviewDto> reviewlist = new ArrayList<>();

        try {
            String reviewid = reviewDto.getOriginalpostid();
            List<ReviewEntity> reviewOptional = reviewRepository.findByOriginalpostid(reviewid);

            for(ReviewEntity entity : reviewOptional) {
                ReviewDto reviewDtos = new ReviewDto();
                reviewDtos.setReviewid(String.valueOf(entity.getReviewid()));
                reviewDtos.setOriginalpostid(entity.getOriginalpostid());
                reviewDtos.setReviewuser(entity.getReviewuser());
                reviewDtos.setReviewusername(entity.getReviewusername());
                reviewDtos.setReviewcontent(entity.getReviewcontent());

                List<String> reviewimgurls = new ArrayList<>();
                for (int i = 0; i < entity.getReviewimage().size(); i++) {
                    String reviewimgurl = entity.getReviewimage().get(i).getReviewimgpath();
                    reviewimgurls.add(reviewimgurl);
                }
                reviewDtos.setReviewimgurl(reviewimgurls);
                reviewlist.add(reviewDto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return reviewlist;
    }
    //게시글 수정
    public String updateReview(ReviewDto reviewDto){
        try{
            Long reviewId = Long.valueOf(reviewDto.getReviewid());
            List<MultipartFile> newImages = reviewDto.getReviewimg();
            List<String> oldImages = reviewDto.getReviewoldimg();

            Optional<ReviewEntity> reviewOptional = reviewRepository.findById(reviewId);

            if (reviewOptional.isPresent()) {
                ReviewEntity reviewEntity = reviewOptional.get();

                if (!reviewEntity.getReviewuser().equals(reviewDto.getReviewuser())) {
                    return "해당 글의 작성자가 아닙니다.";
                }

                reviewEntity.getReviewimage().forEach(image -> image.setReviewentity(null));
                reviewEntity.getReviewimage().clear();

                if (oldImages != null && !oldImages.isEmpty()) {
                    List<ReviewImgEntity> oldImageEntities = saveoldImages(oldImages, reviewEntity);
                    reviewEntity.getReviewimage().addAll(oldImageEntities);
                }

                List<ReviewImgEntity> newImageEntities = saveImages(newImages, reviewEntity);
                reviewEntity.getReviewimage().addAll(newImageEntities);

                reviewEntity.setReviewcontent(reviewDto.getReviewcontent());

                reviewRepository.save(reviewEntity);

                return "게시글 수정완료";
            }else{
                return "해당하는 글이 존재하지 않습니다.";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "게시글 수정 실패";
        }
    }

    //방문자수 카운팅
    public String visitcount(ReviewDto reviewDto){
        try{
            Long reviewId = Long.valueOf(reviewDto.getReviewid());
            Optional<ReviewEntity> reviewOptional = reviewRepository.findById(reviewId);

            if (reviewOptional.isPresent()) {
                Optional<UserEntity> userOptional = userRepository.findByUser(reviewDto.getReviewuser());

                if (userOptional.isPresent()) {
                    Optional<VisitidEntity> visitidOptional = visitidRepository.findByVisitid(String.valueOf(reviewId));
                    if(visitidOptional.isPresent()){
                        return("이미 방문등록을 하셨습니다.");
                    }else{
                        UserEntity newuser = userOptional.get();
                        VisitidEntity newvisitid = new VisitidEntity();
                        newvisitid.setVisitid(String.valueOf(reviewId));
                        newvisitid.setUserentity(newuser);

                        visitidRepository.save(newvisitid);
                    }
                }else{
                    return("유저가 존재하지 않습니다!");
                }

                ReviewEntity reviewEntity = reviewOptional.get();

                long visitcount = reviewEntity.getVisitcount();
                visitcount ++;

                reviewEntity.setVisitcount(visitcount);

                reviewRepository.save(reviewEntity);

                return "방문지 등록 완료.";
            }else{
                return "해당하는 글이 존재하지 않습니다.";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "방문지 등록 실패";
        }
    }

    //방문자수 언카운팅
    public String visituncount(ReviewDto reviewDto){
        try{
            Long reviewId = Long.valueOf(reviewDto.getReviewid());
            Optional<ReviewEntity> reviewOptional = reviewRepository.findById(reviewId);
            if (reviewOptional.isPresent()) {
                Optional<UserEntity> userOptional = userRepository.findByUser(reviewDto.getReviewuser());

                if (userOptional.isPresent()) {
                    Optional<VisitidEntity> visitidOptional = visitidRepository.findByVisitid(String.valueOf(reviewId));

                    if(visitidOptional.isPresent()){
                        UserEntity userEntity = userOptional.get();

                        VisitidEntity visitidToRemove = visitidOptional.get();
                        userEntity.getVisitid().remove(visitidToRemove);

                        userRepository.save(userEntity);
                    } else {
                        return "방문 정보가 없습니다!";
                    }

                } else {
                    return "유저가 존재하지 않습니다!";
                }
            }else{
                return "해당글이 존재하지 않습니다.";
            }

            ReviewEntity reviewEntity = reviewOptional.get();

            long visitcount = reviewEntity.getVisitcount();
            visitcount --;

            reviewEntity.setVisitcount(visitcount);

            reviewRepository.save(reviewEntity);

            return "방문 등록 취소 완료.";
        }catch(Exception e){
            e.printStackTrace();
            return "방문 등록 취소 실패";
        }
    }

    //게시글 삭제
    public String deleteReview(ReviewDto reviewDto){
        try{
            Optional<UserEntity> alluserOptional = userRepository.findUserWithNonEmptyScraps();
            Optional<ReviewEntity> reviewOptional = reviewRepository.findById(Long.valueOf(reviewDto.getReviewid()));
            Optional<ScrapEntity> scrapOptional = scrapRepository.findByScrapid(String.valueOf(reviewDto.getReviewid()));

            if (reviewOptional.isPresent()) {
                ReviewEntity reviewEntity = reviewOptional.get();

                if (!reviewEntity.getReviewuser().equals(reviewDto.getReviewuser())) {
                    return "해당 게시글의 작성자가 아닙니다. 삭제할 수 없습니다.";
                }

                if (scrapOptional.isPresent()) {
                    UserEntity userEntity = alluserOptional.get();

                    ScrapEntity scrapToRemove = scrapOptional.get();
                    userEntity.getScraps().remove(scrapToRemove);

                    userRepository.save(userEntity);
                }

                reviewRepository.deleteById(Long.valueOf(reviewDto.getReviewid()));

                Optional<UserEntity> userOptional = userRepository.findByUser(reviewDto.getReviewuser());

                if (userOptional.isPresent()) {
                    Optional<ReviewWriteidEntity> writeidOptional = writeidRepository.findByReviewwriteid(String.valueOf(reviewDto.getReviewid()));

                    if (writeidOptional.isPresent()) {
                        UserEntity userEntity = userOptional.get();

                        ReviewWriteidEntity writeidToRemove = writeidOptional.get();
                        userEntity.getReviewwriteid().remove(writeidToRemove);

                        userRepository.save(userEntity);
                    }
                } else {
                    return "해당하는 유저가 존재하지 않습니다.";
                }
            }else {
                return "해당하는 게시글이 존재하지 않습니다.";
            }

            return "게시글 삭제 완료!";
        }catch (Exception e){
            e.printStackTrace();
            return "게시글 삭제 실패";
        }
    }



    //이미지 저장
    private List<ReviewImgEntity> saveImages(List<MultipartFile> images, ReviewEntity reviewEntity) {
        List<ReviewImgEntity> imageEntities = new ArrayList<>();
        try {
            if (images != null) { // 이미지가 첨부되었는지 확인
                for (MultipartFile image : images) {
                    // 이미지 저장 폴더 경로 설정
                    String uploadDir = "uploads/";
                    File uploadPath = new File(uploadDir);
                    if (!uploadPath.exists()) {
                        uploadPath.mkdirs();
                    }

                    String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

                    Path filePath = Paths.get(uploadPath.getAbsolutePath() + File.separator + fileName);

                    Files.write(filePath, image.getBytes());

                    ReviewImgEntity reviewimgEntity = new ReviewImgEntity();
                    reviewimgEntity.setReviewimgpath(uploadDir + fileName);
                    reviewimgEntity.setReviewentity(reviewEntity); // AuctionEntity 설정
                    imageEntities.add(reviewimgEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageEntities;
    }

    //오래된 이미지 저장
    private List<ReviewImgEntity> saveoldImages(List<String> oldImageNames, ReviewEntity reviewEntity) {
        try {
            return oldImageNames.stream()
                    .map(imageName -> {
                        ReviewImgEntity reviewImgEntity = new ReviewImgEntity();
                        reviewImgEntity.setReviewimgpath(imageName);
                        reviewImgEntity.setReviewentity(reviewEntity);
                        return reviewImgEntity;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // 예외 발생 시 로깅
            System.err.println("Error while creating AuctionImageEntity: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
