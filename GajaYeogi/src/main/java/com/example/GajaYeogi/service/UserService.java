package com.example.GajaYeogi.service;

import com.example.GajaYeogi.dto.UserDto;
import com.example.GajaYeogi.entity.*;
import com.example.GajaYeogi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    
    //유저 정보 저장
    public String saveuser(UserDto userDto){
        try {
            String user = userDto.getUser();
            String username = userDto.getUsername();

            Optional<UserEntity> userOptional = userRepository.findByUser(user);

            if (userOptional.isPresent()) {
                return "이미 존재하는 사용자입니다.";
            }

            UserEntity userEntity = new UserEntity();
            userEntity.setUser(user);
            userEntity.setUsername(username);
            userEntity.setIntroduction("자기소개를 넣어주세요.");

            userRepository.save(userEntity);

            return "사용자 추가 완료";
        } catch (Exception e) {
            e.printStackTrace();
            return "사용자 추가 실패";
        }
    }

    //유저 조회
    public UserDto readuser(UserDto userDto){
        UserDto userdtos = new UserDto();
        try{
            String user = userDto.getUser();

            Optional<UserEntity> userOptional = userRepository.findByUser(user);

            if (userOptional.isPresent()) {
                UserEntity userEntity = userOptional.get();

                userdtos.setUserid(userEntity.getUserid());
                userdtos.setUser(userEntity.getUser());
                userdtos.setUsername(userEntity.getUsername());
                userdtos.setIntroduction(userEntity.getIntroduction());

                List<String> scrapIds = userEntity.getScraps().stream()
                        .map(ScrapEntity::getScrapid)
                        .collect(Collectors.toList());
                userdtos.setScrapids(scrapIds);

                List<String> postwriteids = userEntity.getPostwriteid().stream()
                        .map(PostWriteidEntity::getPostwriteid)
                        .collect(Collectors.toList());
                userdtos.setPostwriteids(postwriteids);

                List<String> reviewwriteids = userEntity.getReviewwriteid().stream()
                        .map(ReviewWriteidEntity::getReviewwriteid)
                        .collect(Collectors.toList());
                userdtos.setReviewwriteids(reviewwriteids);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return userdtos;
    }

    //자기소개 작성
    public String saveintroduction(UserDto userDto){
        try{
            String user = userDto.getUser();

            Optional<UserEntity> userOptional = userRepository.findByUser(user);

            if (userOptional.isPresent()) {
                UserEntity userEntity = userOptional.get();
                userEntity.setIntroduction(userDto.getIntroduction());
                
                userRepository.save(userEntity);
            }else{
                return("해당하는 유저가 존재하지 않습니다.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return("자기소개 작성 완료.");
    }
}
