# 10_Vue

## 🎄 팀원 🎄

강민정 김다나

## 📆 개발 기간 📆

2023.11.09 - 2023.11.24

## 🚦 기술 스택 🚦

#### Front-end

<img alt="JavaScript" src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white">
<img alt="vue.js" src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">
<img alt="bootstrap" src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">

#### Back-end

<img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/>
<img alt="Spring Boot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=Spring Boot&logoColor=white"/>

#### Database

<img alt="mysql" src ="https://img.shields.io/badge/mysql-003545.svg?&style=for-the-badge&logo=mysql&logoColor=white"/>

#### 버전 및 이슈관리

<img alt="gitlab" src ="https://img.shields.io/badge/gitlab-FC6D26.svg?&style=for-the-badge&logo=gitlab&logoColor=white"/>

#### 협업 툴

<img alt="notion" src ="https://img.shields.io/badge/notion-000000.svg?&style=for-the-badge&logo=notion&logoColor=white"/>
<img alt="mattermost" src ="https://img.shields.io/badge/mattermost-0058CC.svg?&style=for-the-badge&logo=mattermost&logoColor=white"/>

## ⛏ 구현 기능 개요 ⛏

### 기능 1. 회원 관리

- 회원가입
  - 주민번호 AES 암호화
  - 랜덤한 salt를 추가하여 비밀번호 SHA-256 암호화
  - 아이디 중복 방지
  - 입력값 유효성 검사
  - 가입시 사용자 이메일 인증
- 로그인
  - 로그인 시도 횟수 5회 제안
  - 중복 로그인 방지
  - 로그인 시 자동입력 방지
  - 카카오 로그인
- 로그아웃
- 비밀번호 관리
  - 현재 비밀번호와 이메일을 통한 본인인증 후 새로운 비밀번호 변경
  - 비밀번호 유효기간 설정
- 회원 정보 수정
  - 현재 이메일을 입력 받은 후 새로운 이메일 인증 후 변경
- 회원 탈퇴
  - 비밀번호를 입력받아 사용자 인증 후 탈퇴

### 기능 2. 여행 계획 관리

- CRUD
- 타 사용자 언급
- 자신이 언급된 글 확인

### 비기능 1. https 적용

- keytool을 사용한 인증서로 https 적용

### 비기능 2. Back-End에서의 유효성 검사

- 모든 Dto, Vo의 getter에서 null 체크 및 유효성 검사

### 비기능 3. 세션 사용

### 비기능 4. 주석문 안에 포함된 시스템 주요 정보 제거 / 제거되지 않고 남은 디버그 코드 제거 / 오류메시지 정보 노출 방지

<br>

## 🛠 구현기능 상세 🛠

#### 메인 화면

<img src="img/main.png" width="500"/><br>

### 1. 로그인 보안 처리

<br>
<img src="img/register.png" width="500"/><br>
<br>
회원가입 칸
<br> 
<br>
<img src="img/register2.png" width="500"/><br>
저장된 유저의 정보 
<br>
<br>
<img src="img/register3.png" width="500"/><br>
저장된 키와 salt

#### 로그인 화면

<br>
<img src="img/login.png" width="500"/><br>
<img src="img/login2.png" width="500"/><br>
<img src="img/login3.png" width="500"/><br>
<img src="img/login4.png" width="500"/><br>
<br>
<br>
<br>
로그인을 하면, 생성된 jwt access, refresh token과 user_id 를 SessionStroage 에 저장하여
로그아웃 버튼을 보여지게 하고 로그인 상태를 유지하도록 했다.

### 2. 계획 기능

#### 여행 계획 등록

![여행계획](/uploads/6f4b7427941b708249a6aaf13e43b5d2/여행계획.png)
<br>
사용자가 입력한 여행 계획

```javascript
// plan.js
function writePlan(param, success, fail) {
  param.access_token = sessionStorage.getItem("access_token");
  local.post(`/plan/regist`, param).then(success).catch(fail);
}

// planWriteView.vue
function registPlan() {
  const body = {
    userid: userid.value,
    title: title.value,
    content: content.value,
    mentionedid: mentionedid.value.split(","),
  };
  writePlan(
    body,
    ({ data }) => {
      console.log(data);
    },
    (err) => {
      console.log(err);
    }
  );
}
```

위 함수들을 이용하여 객체 형태로 Back에 사용자의 정보를 전달함.

![여행계획DB](/uploads/80f4c7f2ee1b3c49eba8e869bc4f2412/여행계획DB.png)
<br>
여행 계획 DB에 저장된 사용자의 정보와 사용자의 글

![여행계획멘션DB](/uploads/bf187a8d75a8c7883bcbaed3c6388e6b/여행계획멘션DB.png)
<br>
멘션 DB에 저장된 언급된 사용자와 언급된 글의 정보

#### 여행 계획 목록

![여행계획목록](/uploads/e7d378020edadbec30f1f3f60e56c005/여행계획목록.png)
<br>
DB에 저장된 모든 여행 계획을 확인함.

```vue
<tr class="text-conter" v-for="plan in plans" :key="plan.contentid">
    <td>
        <router-link :to="{name:'plan-view', params:{contentid:plan.contentid}}">
            {{ plan.title }}
        </router-link>
    </td>
    <td>{{ plan.userid }}</td>
    <td>{{ plan.timestamp }}</td>
</tr>
```

table의 형태로 v-for을 사용하여 주어진 계획들을 랜더링함.

#### 여행 계획 상세 조회

![상세조회](/uploads/dfe4646d57a22cfa63944048fe8b377a/상세조회.png)
<br>
조회를 원하는 계획의 제목을 누르면 해당 계획의 상세한 정보를 볼 수 있음.

```javascript
// plan.js
function detailPlan(contentid, success, fail) {
  local.get(`/plan/view/${contentid}`).then(success).catch(fail);
}

// planView.vue
function getPlan(contentid) {
  detailPlan(
    contentid,
    ({ data }) => {
      console.log(data);
      plan.value = data;
    },
    (err) => {
      console.log(err);
    }
  );
}

onMounted(() => {
  console.log(route.params.contentid);
  getPlan(route.params.contentid);
});
```

onMounted를 사용하여 component가 붙혀졌을 때 위의 함수를 실행함. PK인 contentid를 사용하여 해당 계획을 조회함

### 3. 언급 기능

#### 알림 확인

![알림확인](/uploads/04a650cb09822e05a84fbc14643498bd/알림확인.gif)
<br>
알림의 제목을 누르면 해당 계획의 상세 정보를 조회할 수 있음.
<br>
이 때, aa 사용자로 로그인 한 상태이기 때문에 aa 사용자가 언급된 계획만 볼 수 있음.

<br>
![알림확인후](/uploads/df58584ce20ec258b4e4c856f9fb84ce/알림확인후.gif)
<br>
알림을 확인하고 다시 알림창으로 돌아오면 해당 알림은 사라짐.
<br>
![멘션확인후](/uploads/efe0c16bf245344c783595d3187bbddf/멘션확인후.png)
<br>
알림 확인 후 DB에서 isChecked가 true로 바뀐 것을 확인 할 수 있음.

```vue
<tbody>
    <tr class="text-conter" v-for="mention in mentions.filter((m)=> !m.checked)" :key="mention.contentid">
        <td>
            <router-link :to="{name:'plan-view', params:{contentid:mention.contentid}}"  @click.native="clickMention(mention)">
                {{ mention.title }}
            </router-link>
        </td>
        <td>{{ mention.userid }}</td>
    </tr>
</tbody>
```

v-for문에서 filter를 사용하여 checked가 false인, 즉 확인하지 않은 알림만 table에 넣어줌.

## ✨ 역할 분담 ✨

### 강민정

- 주민번호 AES 암호화
- 현재 비밀번호와 이메일을 통한 본인인증 후 새로운 비밀번호 변경
- 중복 로그인 방지
- 여행 계획 작성
- 여행 계획 목록 전체 조회 및 상세 조회
- 여행 계획 수정
- 여행 계획 삭제
- 언급 관리
- Back-End에서의 유효성 검사

## 🎁 프로젝트 소감 🎁

<b>강민정</b>

```text
소프트웨어 개발 보안 가이드의 구현단계 보안 약점 제거 기준 체크리스트를 적용하면서 총 20가지 항목을 확인했습니다.
이 과정에서 보안이란 끝이 없다는 것을 깨달았습니다.
그리고 짧은 기간 동안 이러한 보안 취약점을 대비하는 과정에서 보안의 중요성을 다시 한 번 느끼게 되었습니다.
또한, 보안은 계속해서 새로운 문제가 나타나기 때문에 저희 프로그램이 얼마나 취약한지 다시 한 번 깨달았습니다.
하지만 이 사실을 인지하고 앞으로 더 신중하게 프로그래밍을 하고 보안에 신경을 써야 한다는 것 자체가 저희 프로젝트의 성과라고 생각합니다.
```

<b>김다나</b>

```text
  Vue.js 라는 javaScript Framework 에 대해 학습하고, 지금까지 jsp 로 구현해왔던 웹페이지들을 Vue 를 이용하여 구현해보았다.
 한 페이지 안에서 여러가지 컴포넌트, item 들을 나눠서 구현하고 반응형 데이터들을 바인딩하며 템플릿에 적용시켰다. 그리고 Vite 라는 빌드 툴을 사용하여
 환경 설정과 개발 환경을 실행하여 구현했고, DOM Tree 구조에 대해 더 자세히 이해할 수 있는 시간이었다.
  Back-End 부분에서는 HttpSession 이 아닌 개발 환경 편의성을 위해 JWT (Java Web Token) 을 이용해보았다.
HttpSession 을 사용할 수 없는 환경이기때문에 JWT 를 사용하게 되었는데, JWT 의 구성요소와 유의할 점에 대해 알아볼 수 있는 시간이었다.
JWT 는 db access 가 자주 일어나고 정보가 모두 들어있기 때문에 할 수 있다면 HttpSession를 사용하는 것이 좋을 것 같다는 생각이 들었다.
 이번 프로젝트를 통해 풀스택 개발자로서 Front-End 와 Back-End 두 분야를 탐구하며 개발 역량을 함양하고, 개발자로서의 가치관을 좀 더 정립할 수
있는 기회를 가졌다.

```
