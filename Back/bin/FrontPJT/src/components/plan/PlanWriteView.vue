<script setup>
import { useRouter } from "vue-router";
import { writePlan } from "@/api/plan";

const router = useRouter();

function moveList() {
    router.push({name:'plan-list'})
}

function registPlan() {
    const body = {
      userid: userid.value,
      title: title.value,
      content: content.value,
      mentionedid: mentionedid.value.split(","),
      access_token: sessionStorage.getItem("access_token")
    }
    writePlan(body,
        ({ data }) => {
          console.log(data);
          alert(data.msg);
          moveList();
        },
        (err) => {
          console.log(err)
    })
}

</script>


<template>
    <div class="box">
      <div class="mb-3">
        <label class="form-label">여행자 ID :</label>
        <input id="userid" type="text" class="form-control" />
      </div>

      <div class="mb-3">
        <label class="form-label">제목 :</label>
        <input id="title" type="text" class="form-control" />
      </div>
  
      <div class="mb-3">
        <label class="form-label">계획 :</label>
        <textarea id="content" class="form-control" rows="3"></textarea>
      </div>

      <div class="mb-3">
        <label class="form-label">언급할 사람을 쉼표로 구분하여 입력해주세요.</label>
        <input id="mentionedid" type="text" class="form-control" />
      </div>
  
      <button type="button" class="btn btn-outline-primary btn-sm" @click="registPlan">계획 등록</button>
      <button class="btn btn-outline-primary btn-sm" @click="moveList">목록으로</button>
    </div>
</template>

<style scoped>

</style>