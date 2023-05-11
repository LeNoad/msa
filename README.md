# MSA - Spring Boot

## URI
1. /api/authorize => 로그인
2. /api/authorizeTokenRefresh => 토큰 갱신 ( 로그인 후 만기된 토큰값을 refreshToken으로 갱신)
** HTTP HEADER **
  KEY : Authorization
  value : bearer ${refreshToken}
 
