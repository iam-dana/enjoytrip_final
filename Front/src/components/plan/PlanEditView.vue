<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { updatePlanView, updatePlan } from "@/api/plan";

const router = useRouter();

function moveList() {
  router.push({ name: 'plan-list' });
}

const route = useRoute();

const plan = ref({});

function getUpdate(contentid) {
    updatePlanView(
        contentid,
      ({ data }) => {
        if (data.msg == `planUpdateView ok`) {
          console.log(data);
            plan.value = data;
        } else {
          alert(data.msg);
          moveList();
        }
        },
        (err) => {
            console.log(err);
        }
    )
}

onMounted(() => {
    getUpdate(route.params.contentid);
})

function postUpdate() {
    const body = {
        title: title.value,
        content: content.value, 
        contentid: route.params.contentid,
        mentionedid: mentionedid.value.split(",")
    }
    updatePlan(body,
        ({ data }) => {
            console.log(data)
            alert(data.msg);
            moveList();
        },
        (err) => {
          console.log(err)
        }
    )
}

</script>


<template>
    <div class="box">
      <div class="mb-3">
        <label class="form-label">여행자 ID : {{ plan.userid }}</label>
      </div>

      <div class="mb-3">
        <label class="form-label">제목 :</label>
        <input id="title" :value="plan.title" type="text" class="form-control" />
      </div>
  
      <div class="mb-3">
        <label class="form-label">계획 :</label>
        <textarea id="content" :value="plan.content" class="form-control" rows="3"></textarea>
      </div>

      <div class="mb-3">
        <label class="form-label">언급할 사람을 쉼표로 구분하여 입력해주세요.</label>
        <input id="mentionedid" :value="plan.mentionList" type="text" class="form-control" />
      </div>
  
      <button type="button" class="btn btn-outline-primary btn-sm" @click="postUpdate">계획 등록</button>
      <button class="btn btn-outline-primary btn-sm" @click="moveList">목록으로</button>
    </div>
</template>

<style scoped>

</style>