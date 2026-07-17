# HAPHAP-ANDROID

**합합**은 채용 공고 지원 이후 발생하는 정보 공백을 줄이기 위한 채용 결과 공유 플랫폼입니다.
<br/><br/>
사용자는 공고별 결과 현황, 전형 단계, 예상 발표일 등을 확인하며 현재 자신의 상황을 보다 명확하게 파악할 수 있습니다. 또한 알림, 합격 카드 등을 통해 지원 이후의 과정을 트래킹하고 합격에 대한 긍정적인 경험을 제공합니다.
<br/>
흩어져 있는 채용 결과 정보를 한곳에 모아 기다림의 시간을 함께 보내고, 더 선명한 정보 속에서 불확실성과 불안을 줄이도록 돕습니다.



## **✨ Contributors**

|            😻이지민(LEAD)<br/>[@vahkjsdf](https://github.com/vahkjsdf)           |            😻박찬미<br/>[@chanmi1125](https://github.com/chanmi1125)            |            😻강승희<br/>[@seunghee0321](https://github.com/seunghee0321)         |                                                                          😻송서현<br/>[@Hiimynameiss](https://github.com/Hiimynameiss)                                                                          |               😻박지영<br/>[@jiyoung2ee](https://github.com/jiyoung2ee)          |
|:--------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/60a5296c-6f83-4ac0-b37b-16d248ae8b20" width ="220" /> | <img src="https://github.com/user-attachments/assets/202fb383-ba66-4d6e-8a69-12a4df856b62" width="200" /> | <img src="https://github.com/user-attachments/assets/3e7f40c0-952d-4890-ad49-439916a4995e" width="200" /> | <img src="https://github.com/user-attachments/assets/5bfc278e-1571-444d-be11-5057f0aedbf5" width="200" /> | <img src="https://github.com/user-attachments/assets/125891c1-f8e5-4c91-be3f-c3732e7c5b93" width="200" /> |
|                        `검색`,`공고리스트`, `알림`                               |                                `캘린더`, <br/>`마이페이지`                                          |                            `상세페이지`                                      |                                                                                    `카카오 로그인`,<br/> `상태 등록하기`,<br/> `합격카드`                                                                                    |                                       `홈`                                       |

<br/>
<br/>

## **💘 Contents**


<img width="1920" height="1080" alt="93" src="https://github.com/user-attachments/assets/739d3c61-417e-4397-b040-8c4872296f1c" />
<img width="1920" height="1080" alt="94" src="https://github.com/user-attachments/assets/0d63d740-2361-4806-800f-1fd322e7fe4f" />
<img width="1920" height="1080" alt="95" src="https://github.com/user-attachments/assets/b8d34527-df3f-461a-8074-55c5bc69d306" />
<img width="1920" height="1080" alt="97" src="https://github.com/user-attachments/assets/e40a4fd9-d26d-4851-ad9e-99fd24bea06f" />



<br/>
<br/>

## **⚒️ Tech Stacks**

| 항목 | 기술 스택                                                  |
| :--- |:-------------------------------------------------------|
| Architecture | Google Recommended Architecture                        |
| Pattern | MVVM                                                   |
| DI | Hilt                                                   |
| Asynchronous | Coroutine, Flow                                        |
| Network | Retrofit2, OkHttp                                      |
| Navigation | Single Activity Architecture (SAA), Jetpack Navigation |
| UI Framework | Jetpack Compose                                        |
| Image Processing | Coil, Lottie                                          |
| Logging | Timber                                                 |

<br/>



<br/>

## **📜 Convention**

- [**Github Convention**](https://app.notion.com/p/Github-Convention-38c51c3cf3c780e4a91ecbbc18cbdc49?source=copy_link)
- [**Naming Convention**](https://app.notion.com/p/Naming-Convention-38c51c3cf3c7806ab402ea87b6d62a0e?source=copy_link)

<br/>

## **🗂️ Project Structure**
```text
🗃️ haphap
└── 📂 core
│   ├── 📂 designsystem       
│   │   ├── 📁 component       # 공통 컴포넌트
│   │   └── 📁 theme           # 색상, 타이포, 테마
│   ├── 📂 extensions          # Kotlin 확장 함수
│   ├── 📂 navigation          # 네비게이션 라우트 정의
│   ├── 📂 network             # 네트워크 설정
│   │   └── 📁 di              # 네트워크 관련 의존성 주입 모듈
│   └── 📁 util                # 공통 유틸
│
├── 📂 data
│   ├── 📁di                   # data 계층 의존성 주입 모듈
│   ├── 📂 local
│   │   └── 📁 datasource      # LocalDataSource 인터페이스 및 구현체
│   ├── 📁 mapper              # DTO ↔ Model 변환
│   ├── 📁 model               # 도메인 모델
│   ├── 📂 remote
│   │   ├── 📁 datasource      # RemoteDataSource 인터페이스 및 구현체
│   │   ├── 📁 dto             # API 요청/응답 DTO
│   │   └── 📁 service         # Retrofit Service 인터페이스
│   └── 📁 repository          # Repository 인터페이스 및 구현체
│
└── 📂 presentation
    ├── 📁 calendar
    ├── 📁 home
    ├── ...
    └── 📁 main                # MainActivity, 네비게이션 정의
