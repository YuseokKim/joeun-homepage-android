# joeun-homepage-android
조은건설 홈페이지 푸시전용 안드로이드입니다.

## 사용 기술 스펙
-  Retrofit2, Firebase Cloud Messaging

## 서비스 소개
- 안성 조은건설중기(http://www.joeunjunggi.co.kr) 앱 영역 일부분입니다.
  * 클라이언트 영역은 HTML와 Javascript로 구현, 서버는 Java와 Springboot로 구현, 서버와 클라이언트는 물리적으로 분리했습니다.
- Retrofit으로 서버에 FCM 토큰 정보를 전송합니다.
- 실제 견적문의 화면(http://www.joeunjunggi.co.kr/contact-us.html)에서 견적 문의 발생 시 저장된 FCM 토큰을 조회하여 문의내용을 푸시알림 전송합니다.
