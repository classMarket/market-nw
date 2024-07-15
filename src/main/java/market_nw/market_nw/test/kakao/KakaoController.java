//package market_nw.market_nw.test.kakao;
//
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//public class KakaoController {
//
//    private final KakaoService kaKaoLoginService;
//    private final MemberService memberService;
//    private final MemberRepository memberRepository;
//    private final JwtProvider jwtProvider;
//
//    //카카오 로그인 코드
//    @ResponseBody
//    @PostMapping("/oauth/kakao")
//    public BaseResponse<?> kakaoCallback(@RequestParam("accToken") String accessToken,
//                                         @RequestParam("refToken") String refreshToken) {
//        String memberEmail = kaKaoLoginService.getMemberEmail(accessToken);//이메일 가져오는것
//        String memberNickName = kaKaoLoginService.getMemberNickname(accessToken);//가져오는것
//        Optional<Member> findMember = memberRepository.findByEmail(memberEmail);//계정찾고
//        if (!findMember.isPresent()) {//계정있으면
//            Member kakaoMember = new Member();
//            kakaoMember.updateEmail(memberEmail);
//            kakaoMember.updateNickName(memberNickName);
//            kakaoMember.updateIsSocialLogin();
//            JwtResponseDTO.TokenInfo tokenInfo = JwtResponseDTO.TokenInfo.builder()
//                    .accessToken(accessToken)
//                    .refreshToken(refreshToken)
//                    .build();
//            kakaoMember.updateAccessToken(accessToken);
//            kakaoMember.updateRefreshToken(refreshToken);
//            memberRepository.save(kakaoMember);
//            return new BaseResponse<>(tokenInfo);
//        }
//
//        else { //계정없으면
//            Member member = findMember.get();
//            JwtResponseDTO.TokenInfo tokenInfo = jwtProvider.generateToken(member.getId());
//            member.updateRefreshToken(tokenInfo.getRefreshToken());
//            member.updateIsSocialLogin();
//            memberRepository.save(member); //새로 계정 만들기
//            return new BaseResponse<>(tokenInfo);
//        }
//    }
//
//
//    //카카오 로그아웃 코드
//    @GetMapping("/oauth/kakaologout")
//    @ResponseBody
//    public BaseResponse<?> kakaoLogout(@RequestParam("state") String accessToken)
//    {
//        try{
//            return memberService.logout(accessToken);
//        } catch(Exception e){
//            return new BaseResponse<>(BaseResponseStatus.KAKAO_ERROR);
//        }
//    }
//}
//
