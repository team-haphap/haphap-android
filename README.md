# HAPHAP-ANDROID
---
**합합**은 채용 공고 지원 이후 발생하는 정보 공백을 줄이기 위한 채용 결과 공유 플랫폼입니다.
<br/>
흩어져 있는 채용 결과 정보를 한곳에 모아 기다림의 시간을 함께 보내고, 더 선명한 정보 속에서 불확실성과 불안을 줄이도록 돕습니다.

<br/>

## **✨ Contributors**

|            😻이지민(LEAD)<br/>[@vahkjsdf](https://github.com/vahkjsdf)           |            😻박찬미<br/>[@chanmi1125](https://github.com/chanmi1125)            |            😻강승희<br/>[@seunghee0321](https://github.com/seunghee0321)         |             😻송서현<br/>[@Hiimynameiss](https://github.com/Hiimynameiss)        |               😻박지영<br/>[@jiyoung2ee](https://github.com/jiyoung2ee)          |
|:--------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|
| <img src="https://i.namu.wiki/i/Rti8L28WGRCPo4xGjg5KDzFhWGSRsmYH_F4wWPuKE6EfsYTBtWPG9NNC83oSsw-Ad6ZY2KcuWTSi06WAaXifYSvW39r1Pt3XXpXxHFTqR82_S14U4mozWroeU5uFO_PM0cc-oAZfbaaTmjXpLzitNA.webp" height="200" /> | <img src="https://i.namu.wiki/i/DGwSB9Pi-Ix9ZuM4xnka3eduALV7vkdhyJcZpPCj7cxUyIu262f96qKGyGcXNhv9vh4zClisSW-ZJihHBBUv_bEAXjoP5gGbB-Gn_CKkZ9D5RRDZ_yd2nO_BfxG8RDu3kDLPvTBAKuexGNAPsrpweA.webp" height="200" /> | <img src="https://i.namu.wiki/i/IRdFZU8JBhS1STIQyqdG53mIAIaYNy4kRwp2yuabNQ-kboh6IeElNcBMOHICjMnJpeYXcIojSC7KDZr0YK1QkEmMomcnKcxvkjzSeApMoyVWPvMkfHw6plr2fIpVAM--inXjhoOui-YN5lqk8A078g.webp" height="200" /> | <img src="https://i.namu.wiki/i/s0UwHGQBooU9zJ29DYPTtk42MzwcyirUugyKvcwLKb-hCeZu_LI4tmMDU3a9pvhBeSg0bxjK2ArojF_jUkvaB7PSQP8zkfj7o4hsal4Id12baTum_01ducXMvPTplieTIg5901dn3bkVVrwW5CZ5xA.webp" height="200" /> | <img src="https://i.namu.wiki/i/lTIDnZTRa9omGtUKKbJvSr5-45QgFCAK8Co47FwkMq8XQOBAJAb_hLDUBKCdsFevuTXaK9YtdIQi_wZzA3_WLrOmTzDm_B3lD604njxgEswL6Luyoxpi8CQcfhrte3cQE_Sej48912yXENc0nYcdiQ.webp" height="200" /> |
|                        `검색`, `알림`, `공고리스트`                               |                                `캘린더`                                          |                            `공고 상세페이지`                                      |                  `카카오 로그인`, `상태 등록하기`, `합격카드`                      |                                       `홈`                                       |

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
