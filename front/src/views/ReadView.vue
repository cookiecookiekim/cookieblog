<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

const props = defineProps({
  postId: {
    type: [Number , String],
    require: true,
  },
});

const post = ref({
  id:0,
  title:"",
  content: "",
})

const moveToEdit = () => {
  router.push({name : "edit", params : {postId: props.postId}})
}
onMounted(()=>{
  console.log("props.postId : ", props.postId);
  axios.get(`/api/post/${props.postId}`).then(response => {
    post.value = response.data;
      console.log("response : " ,response);
  });
})
</script>

<template>
  <el-row>
    <el-col>
      <h2 class="title">{{ post.title }}</h2>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2025-06-17 18:39:23</div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{post.content}}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
      <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
  margin:0;
}

.content {
  font-size: 0.85rem;
  margin-top: 12px;
  color: #4e4e4e;
  white-space: break-spaces; // 이거 해야 글 내용이 띄어쓰기와 줄바꿈 적용됨.
  line-height: 1.5;
}

.sub {
  margin-top: 10px;
  font-size: 0.78rem;

  .regDate {
    color: #6b6b6b;
    margin-left: 10px;
  }
}
</style>
