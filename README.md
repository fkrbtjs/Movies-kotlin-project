# Movies-kotlin-project

## 개 요

- Naver 검색api,kotlin,sqlite를 활용하여 제작한 영화검색어플


## 개발환경

| 구 분 | 내 용 |
| --- | --- |
| OS | Windows 10 home |
| Language | Kotlin |
| Editor | Android Studio Dolphin |
| DB | SQLite |
| API | https://developers.naver.com/docs/serviceapi/search/movie/movie.md#%EC%98%81%ED%99%94<br>Naver 검색 API|
| Github | https://github.com/fkrbtjs/Movies-kotlin-project.git |


## 개발기간

2023.02.04(토) ~ 2023.02.08(수)


## 화면구성 및 기능

- 최초 api 로드할 경우 db저장(db에 모두 저장되었다면 api 더이상 로드하지 않고 db에서 데이터를 가져옴)
- 리스트 클릭시 해당영화 정보를 보여주는 웹뷰로 이동
- 영화를 검색할 때마다 검색어를 db에 저장하고 최근검색 버튼 클릭시 최근 검색했던 검색어를 보여줌(가장 최근검색어부터 10개까지)
- 최근검색이력에서 검색어를 클릭하면 해당 검색어에 대한 결과를 리스트로 보여줌

https://user-images.githubusercontent.com/115532120/217252509-98d67f52-6b73-484c-96df-02fb618e36b3.mp4

