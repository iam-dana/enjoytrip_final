<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { writePlan, writePlanView } from "@/api/plan";

const router = useRouter();

const userid = ref("");
const userList = ref([]);
const selectedUsers = ref([])
const mentionedid = ref("")

watch(selectedUsers, (newValue) => {
  mentionedid.value = newValue.join(',');
});

onMounted(() => {
  writePlanView(
    ({ data }) => {
      console.log(data);
      if (data.userid) {
        userid.value = data.userid;
        userList.value = data.userList;
      } else {
        alert(data.msg);
        moveList();
      }
    },
    (err) => {
      console.log(err);
    })
})

function moveList() {
    router.push({name:'plan-list'})
}

function registPlan() {
  const body = {
      title: title.value,
      content: content.value,
      mentionedid: mentionedid.value.split(",")
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
        <label class="form-label">여행자 ID : {{ userid }}</label>
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
        <label class="form-label">아래에서 언급할 사람을 선택해주세요.</label>
        <input v-model="mentionedid" type="text" readonly class="form-control" />
        <select class="form-select" size="3" multiple v-model="selectedUsers">
          <option v-for="user in userList" :key="user" v-if="!selectedUsers.includes(user)">
            {{ user }}
          </option>
        </select>
      </div>
  
      <button type="button" class="btn btn-outline-primary btn-sm" @click="registPlan">계획 등록</button>
      <button class="btn btn-outline-primary btn-sm" @click="moveList">목록으로</button>
    </div>
</template>

<style scoped>

</style>