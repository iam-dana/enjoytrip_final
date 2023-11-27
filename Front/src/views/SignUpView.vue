<script setup>
import { signupMember, idCheckMember } from "../api/member";
import { sendEmail, confirmMail } from "../api/mail";
import { ref, watch } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

///// 회원가입 method/////
function signup() {
    const body = {
        userid: userid.value, 
        userpassword: userpassword.value,
        username: username.value, 
        email: email.value, 
        age: age.value,
        rrn: rrn.value
    }
    signupMember(body, ({ data }) => {
        alert(data.msg);
        router.push({ name: 'home' });
    });
}

///// id 중복 체크/////
const id_msg = ref("");
const isNotExistId = ref(false);

function idCheck() {
    const body = {
        userid: userid.value
    }
    idCheckMember(body, ({ data }) => {
        if (data) {
            id_msg.value = "사용 중인 아이디입니다.";
            isNotExistId.value = false;
        } else {
            id_msg.value = "사용 가능한 아이디입니다.";
            isNotExistId.value = true;
        }
    });
}

///// 비밀번호 패턴 검사/////
const pwd_msg = ref("");
const activateBtn = ref(false);
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


const userid = ref("");
const username = ref("");
const userpassword = ref("");
const email = ref("");
const age = ref(0);
const rrn = ref("");

///// 입력값 유효성 검사/////
function checkId(userid) {
    if (userid!=null && userid.length != 0)
        return true;
    return false;
}

function checkName(username) {
    // null 값 검사?
    if (username.length!=0 && username!=null)
        return true;
    return false;
}

function checkEmail(email) {
    // 올바른 이메일 형식을 입력하라?
    if (email.length!=0 && email!=null)
        return true;
    return false;
}

function checkAge(age){
    // 0~100 사이의 정수를 입력하라?
    if (0 <= age && age <= 100)
        return true;
    return false;
}

function checkRN(rrn) {    
    // 12 자리의 숫자를 입력하라?
    if (rrn != null && rrn.length == 12)
        return true;
    return false;
}

watch(userid, (newid) => {
    isNotExistId.value = false;
});

watch(email, () => {
    isConfirmed.value = false
    email_msg.value = ""
    inputString.value = ""
    isMailConfirm.value = false
    isMailValid.value = true
})

watch([userid, username, userpassword, email, age, rrn, isNotExistId], ([new_userid, new_username, new_password, new_email, new_age, new_rrn, new_existCheck]) => {
    isValidFormPassword(new_password);
    if (checkId(new_userid) && checkName(new_username) && checkEmail(new_email) && checkAge(new_age) && checkRN(new_rrn) && pwdCheck.value && new_existCheck && isConfirmed.value) {
        activateBtn.value = true;
    } else {
        activateBtn.value = false;
    }
});



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
        ID<input v-model="userid" id="userid"/>{{"　"}}<button @click="idCheck">아이디 중복 체크</button><span>{{ id_msg }}</span><br> 
        PW<input type="password" v-model="userpassword" id="userpassword"/><div>{{ pwd_msg }}</div><br> 
        NAME<input v-model="username" id="username"/><br>

        Email<input type="email" placeholder="example@exmaple.com" v-model="email" id="email"/>{{"　"}}
        <button @click="checkMailSend" :disabled="isConfirmed">이메일 인증</button>{{"　"}}
        <span v-show="!isMailValid">유효한 이메일 형식이 아닙니다.</span>
        <span v-show="isMailConfirm">인증 번호
            <input :disabled="isConfirmed" v-model="inputString" autocomplete="off">
            <button @click="checkInputNum" :disabled="isConfirmed">인증하기</button>
        </span>
        <span>{{ email_msg }}</span><br> 

        AGE<input v-model="age" type="number" id="age"/><br>
        REGISTER-NUMBER<input v-model="rrn" id="rrn" autocomplete="off"/><br>
        <button :disabled="!activateBtn" @click="signup">회원 가입</button>
    </div>
</template>

<style scoped>

</style>