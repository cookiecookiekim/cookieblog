<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const posts = ref([]); // 반응형으로 바꿔야 렌더링됨

axios.get("/api/post?page=1&size=5")
.then(response => {
  console.log(response);
  response.data.forEach((r:any) => {
    posts.value.push(r); // 반응형일 경우 value로 접근
  });
})

</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div class="title"> <!-- a 태그로 사용하면 api 전체 다시 불러와서 리소스 낭비. 이렇게 하면 싱글로 동작 -->
        <router-link :to="{ name :'read' , params:{ postId: post.id } }">
          {{post.title}}
        </router-link>
      </div>

      <div class="content">
        {{ post.content }}
      </div>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2025-06-17</div>
      </div>
    </li>
  </ul>
</template>

<style scoped lang="scss"> // npm install sass
ul {
  list-style: none;
  padding : 1rem;

  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      color: #7e7e7e;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 8px;
      font-size: 0.78rem;

      .regDate {
        color: #6b6b6b;
        margin-left: 10px;
      }
    }
  }
}
</style>
