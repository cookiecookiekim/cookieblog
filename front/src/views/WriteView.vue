<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import router from "@/router";

const title = ref("");
const content = ref("");

const write = function (){
  axios.post("/api/post", { // vite.config에서 설정 했으므로 http://localhost:8080 는 빼야 된다.
    title: title.value,
    content: content.value
  })
    .then(() => {
      router.replace({name:"home"}) // 글 작성 후 홈 화면으로 가게 설정
    })
  console.log(title.value, content.value)
}

</script>

<template>
  <div>
    <el-input v-model="title" placeholder="제목을 입력해 주세요"/>
<!--  <input type="text" placeholder="제목을 입력해 주세요">-->
  </div>

  <div class="mt-2"> <!-- mt : 마진-->
  <el-input v-model="content" type="textarea" rows="15"/>
  </div>

  <div class="mt-2">
    <div class="d-flex justify-content-end"> <!-- 오른쪽 배치 -->
  <el-button type="primary" @click="write()">작성 완료</el-button>
    </div>
  </div>
</template>

<style></style>
