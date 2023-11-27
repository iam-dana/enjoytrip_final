<script setup>
import { useTodoStore } from "@/stores/todo";
import TodoListItem from "./TodoListItem.vue";

const todoStore = useTodoStore();

const userid = sessionStorage.getItem("userid")

/*
그러면 우선 로그인 한 유저의 아이디를 세션에서 뽑아온 다음에,
localstorage에 저장된 userid 와 일치하는 것만을 뿌려주면 되는 것이 아닌가?
*/


</script>

<template>
  <div class="container text-center">
    <todo-list-item v-for="todo in todoStore.todos.filter(t => userid === t.userid)" :key="todo.id" :todo="todo"></todo-list-item>
  </div>
</template>

<style scoped></style>
