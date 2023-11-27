<script setup>
import { changeUserInfo, checkCurrentEmail, deleteUser } from "@/api/member";
import { sendEmail, confirmMail } from "@/api/mail";
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

///// 메일 변경 method/////
function change() {
    const body = {
        // userid: sessionStorage.getItem("userid"), 
        email: email.value
    }
    changeUserInfo(body, ({ data }) => {
        alert(data.msg);
    });
}

function deleteCookie(name) {
  document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
}

///// 회원 탈퇴 method/////
function deleteuser() {
    const body = {
        userpassword: password.value
    }
    // console.log(body);
    deleteUser(body, ({ data }) => {
        alert(data.msg);
        deleteCookie("userid");
        router.push({ name: "home" });
    });
}

// const curPw = ref("");
// const newPw = ref("");
const password = ref("");
const email = ref("");
const cur_email = ref("");
const isEmailCorrect = ref(false);

function CheckCurrentEmail() {
    const body = {
        email: cur_email.value
    }
    // console.log(body);
    checkCurrentEmail(body, ({ data }) => {
        if (data.msg) {
            alert(data.msg);
        } else {
            if (data.isChecked) {
                isEmailCorrect.value = true;
            } else {
                isEmailCorrect.value = false;
            }
        }
    });
}


// watch([curPw, newPw], ([newCurPw, newNewPw]) => {
//     isValidFormPassword(newCurPw);
//     isValidFormPassword(newNewPw);
//     if (pwdCheck.value) {
//         activateBtn.value = true;
//     } else {
//         activateBtn.value = false;
//     }
// });


watch(email, () => {
    isConfirmed.value = false
    email_msg.value = ""
    inputString.value = ""
    isMailConfirm.value = false
    isMailValid.value = true
})


///// 이메일 인증/////
const email_msg = ref("") // 인증메일 결과 메시지
const inputString = ref("") // 사용자의 입력값
const isMailConfirm = ref(false) // 인증메일 보냈는지 여부
const isMailValid = ref(true) // 유효한 이메일인지 여부
const isConfirmed = ref(false) // 인증 되었는지 여부

// 인증 메일 보내기 
function checkMailSend() {
    isMailValid.value = true; // 초기화
    if (isValidFormEmail(email.value)) { // 유효한 이메일인지 체크 후
        isMailConfirm.value = true; // 인증메일 보냈다
        const body = {
            mail: email.value,
        }
        sendEmail(body, ({ data }) => { // 인증 메일 보내는 함수 호출
            console.log(data);
            if (data.msg !== null) { // 메세지 받았으면
                email_msg.value = data.msg; // 메세지 업데이트
            }
        });
    }   
}

// 이메일 형식 검사
function isValidFormEmail(mail) {
    const validateEmail = /^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\.[A-Za-z0-9\\-]+/

    if(!validateEmail.test(mail)){ // 유효하지 않은 이메일이면
        isMailValid.value = false;
        return false;
    }
    return true;

}

// 인증번호 서버에 보내기
function checkInputNum() {
    const body = {
        mail: email.value, // 사용자의 이메일
        inputString : inputString.value // 사용자의 입력 값
    }
    confirmMail(body, ({ data }) => { 
        console.log(data);
        email_msg.value = data.msg;
        isConfirmed.value = data.isConfirmed;
    });
}


</script>


<template>
    <div>
        <div>현재 이메일을 입력해주세요.</div>
        Email<input type="email" placeholder="example@exmaple.com" v-model="cur_email" id="email"/>{{"　"}}
        <button @click="CheckCurrentEmail">확인</button>{{"　"}}
        <br>
        <br>
        <div>변경하려는 이메일을 입력하고 인증해주세요.</div>

        Email<input :disabled="!isEmailCorrect" type="email" placeholder="example@exmaple.com" v-model="email" id="email"/>{{"　"}}
        <button @click="checkMailSend" :disabled="isConfirmed">이메일 인증</button>{{"　"}}
        <span v-show="!isMailValid">유효한 이메일 형식이 아닙니다.</span>
        <span v-show="isMailConfirm">인증 번호
            <input :disabled="isConfirmed" v-model="inputString" autocomplete="off">
            <button @click="checkInputNum" :disabled="isConfirmed">인증하기</button>
        </span>
        <span>{{ email_msg }}</span><br>

        

        <!-- <div v-show="isConfirmed">
            현재 비밀번호<input v-model="curPw" id="curPw"/><br>
            변경하려는 비밀번호<input v-model="newPw" id="newPw"/>
            <div>{{ pwd_msg }}</div>
        </div> -->

        <button :disabled="!isConfirmed" @click="change">이메일 변경</button>{{ " " }}

        <br>
        <br>
        <div>
            비밀번호 입력<input type="password" v-model="password" id="password"/><br>
        </div>
        <button @click="deleteuser">회원 탈퇴</button>
    </div>
</template>

<style scoped>

</style>