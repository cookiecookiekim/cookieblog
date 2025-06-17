<script setup lang="ts">

import {useRouter} from "vue-router";
import axios from "axios";
import {ref} from "vue";

const router = useRouter();

const post = ref({
  id: 0,
  title: "",
  content: "",
})

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  },
});

axios.get(`/api/post/${props.postId}`).then((response) => {
  post.value = response.data;
});

const edit = () => {
  axios.patch(`/api/post/${props.postId}`, post.value).then((response) => {
    post.value = response.data;
  })
    .then(() => { // 수정하고 바로 home으루
      router.replace({name : "home"})
    })}

</script>

<template>
  <div>
    <el-input v-model="post.title"/>
<!--  <input type="text" placeholder="제목을 입력해 주세요">-->
  </div>

  <div class="mt-2"> <!-- mt : 마진-->
  <el-input v-model="post.content" type="textarea" rows="15"/>
  </div>

  <div class="mt-2">
  <el-button type="warning" @click="edit()">수정 완료</el-button> <!--warning : 노란색-->
  </div>
</template>

<style></style>
