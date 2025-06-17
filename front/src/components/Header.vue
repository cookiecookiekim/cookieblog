
<template>
  <el-header class="header">
    <!--        <RouterLink to="/">Home</RouterLink>-->
    <!--        <RouterLink to="/write">글 작성</RouterLink>-->
    <el-menu :router="true" mode="horizontal">
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/write">글 작성</el-menu-item>
    </el-menu>
  </el-header>

</template>

<script setup lang="ts"></script>

<style scoped>
.header {
  padding: 0;
  height: 60px;
}
</style>
