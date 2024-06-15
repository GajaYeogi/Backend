package com.example.GajaYeogi.service;

import com.example.GajaYeogi.dto.PostDto;
import com.example.GajaYeogi.entity.PostEntity;
import com.example.GajaYeogi.entity.PostImgEntity;
import com.example.GajaYeogi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    //게시글 작성
    public String postwrite(PostDto postDto){
        try{
            PostEntity postEntity = new PostEntity();
            postEntity.setPostuser(postDto.getPostuser());
            postEntity.setPostusername(postDto.getPostusername());
            postEntity.setPosttitle(postDto.getPosttitle());
            postEntity.setPostcontent(postDto.getPostcontent());
            postEntity.setLocation(postDto.getLocation());
            postEntity.setXpoint(postDto.getXpoint());
            postEntity.setYpoint(postDto.getYpoint());

            List<PostImgEntity> imageEntities = saveImages(postDto.getPostimg(), postEntity);
            postEntity.setPostimage(imageEntities);

            postRepository.save(postEntity);

            return "글 작성 성공!";
        }catch(Exception e){
            e.printStackTrace();
            return "505 예기치 못한 오류입니다";
        }
    }

    public List<PostDto> getAllPost(){
        List<PostDto> postlist = new ArrayList<>();

        try{
            List<PostEntity> postEnitites = postRepository.findAll();
            for(PostEntity entity : postEnitites){
                PostDto postDto = new PostDto();
                postDto.setPostid(entity.getPostid());
                postDto.setPostuser(entity.getPostuser());
                postDto.setPostusername(entity.getPostusername());
                postDto.setPosttitle(entity.getPosttitle());
                postDto.setPostcontent(entity.getPostcontent());
                postDto.setLocation(entity.getLocation());
                postDto.setXpoint(entity.getXpoint());
                postDto.setYpoint(entity.getYpoint());

                List<String> postimgurls = new ArrayList<>();
                for(int i = 0; i < entity.getPostimage().size(); i++){
                    String postimgurl = entity.getPostimage().get(i).getPostimgpath();
                    postimgurls.add(postimgurl);
                }
                postDto.setPostimgurl(postimgurls);

                postlist.add(postDto);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return postlist;
    }

    public PostDto getPost(PostDto postDto){
        PostDto postlist = new PostDto();

        try {
            Long postid = postDto.getPostid();
            Optional<PostEntity> postOptional = postRepository.findById(postid);

            if (postOptional.isPresent()) {
                PostEntity entity = postOptional.get();

                postlist.setPostid(entity.getPostid());
                postlist.setPostuser(entity.getPostuser());
                postlist.setPostusername(entity.getPostusername());
                postlist.setPosttitle(entity.getPosttitle());
                postlist.setPostcontent(entity.getPostcontent());
                postlist.setLocation(entity.getLocation());
                postlist.setXpoint(entity.getXpoint());
                postlist.setYpoint(entity.getYpoint());

                List<String> postimgurls = new ArrayList<>();
                for(int i = 0; i < entity.getPostimage().size(); i++){
                    String postimgurl = entity.getPostimage().get(i).getPostimgpath();
                    postimgurls.add(postimgurl);
                }
                postlist.setPostimgurl(postimgurls);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return postlist;
    }

    //이미지 저장
    private List<PostImgEntity> saveImages(List<MultipartFile> images, PostEntity postEntity) {
        List<PostImgEntity> imageEntities = new ArrayList<>();
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

                    PostImgEntity postimgEntity = new PostImgEntity();
                    postimgEntity.setPostimgpath(uploadDir + fileName);
                    postimgEntity.setPostentity(postEntity); // AuctionEntity 설정
                    imageEntities.add(postimgEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageEntities;
    }
}
