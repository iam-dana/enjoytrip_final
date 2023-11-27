<script>
import KakaoMap from "@/components/KakaoMap.vue";
import TourListView from "../components/map/TourListView.vue";

export default {
  name: "App",
  components: {
    KakaoMap, TourListView
  },
};

</script>

<template>
    <div>
        메인입니다.
    </div>
    <KakaoMap />
   
  
  </template>

<style scoped>

</style>