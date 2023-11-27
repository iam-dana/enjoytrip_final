<script setup>
import { changePw, checkCurrentEmail } from "@/api/member";
import { sendEmail, confirmMail } from "@/api/mail";
import { ref, watch } from "vue";

///// 비번변경 method/////
function change() {
    const body = {
        // userid: sessionStorage.getItem("userid"), 
        curPw: curPw.value,
        newPw: newPw.value
    }
    changePw(body, ({ data }) => {
        console.log(data)
        if (data.msg !== null) { // 메세지 받았으면
            alert(data.msg);
        }
    });
}

///// 비밀번호 패턴 검사/////
const pwd_msg = ref("");
const pwdCheck = ref(false);

function isValidFormPassword(pw){
    var check = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;
  
    if(pw.length < 8 || pw.length > 20){
        pwd_msg.value = "비밀번호는 8~20 자리로 입력해주세요.";
        pwdCheck.value = false;
        return false;
    }
    if(!check.test(pw)){
        pwd_msg.value = "비밀번호는 문자, 숫자, 특수문자의 조합으로 입력해주세요."; 
        pwdCheck.value = false;
        return false;
    }
  
    pwd_msg.value = "";
    pwdCheck.value = true;
    return true;
}


const curPw = ref("");
const newPw = ref("");
const activateBtn = ref(true);
const email = ref("");


watch([newPw], ([newNewPw]) => {
    isValidFormPassword(newNewPw);
    if (pwdCheck.value) {
        activateBtn.value = false;
    } else {
        activateBtn.value = true;
    }
});


watch(email, () => {
    isConfirmed.value = false
    activateBtn.value = !isConfirmed.value
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
const cur_email = ref("");
const isEmailCorrect = ref(false);

/// 현재 이메일 맞는지
function CheckCurrentEmail() {
    const body = {
        email: email.value
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
        <div>이메일 인증 후 비밀번호를 변경해주세요.</div><br>

        <div>현재 이메일을 입력해주세요.</div>
        Email<input type="email" placeholder="example@exmaple.com" v-model="email" id="email"/>{{"　"}}
        <button @click="CheckCurrentEmail">확인</button>{{"　"}}

        <button @click="checkMailSend" :disabled="!isEmailCorrect">이메일 인증</button>{{"　"}}
        
        <span v-show="isMailConfirm">인증 번호
            <input :disabled="isConfirmed" v-model="inputString" autocomplete="off">
            <button @click="checkInputNum" :disabled="isConfirmed">인증하기</button>
        </span>
        <span>{{ email_msg }}</span><br>

        <br>

        <div v-show="isConfirmed">
            현재 비밀번호<input type="password" v-model="curPw" id="curPw"/><br>
            변경하려는 비밀번호<input type="password" v-model="newPw" id="newPw"/><div>{{ pwd_msg }}</div><br>
        </div>

        <button :disabled="activateBtn" @click="change">비밀번호 변경</button>
    </div>
</template>

<style scoped>

</style>