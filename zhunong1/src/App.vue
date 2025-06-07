<script setup>

import IconDocumentation from "@/components/icons/IconDocumentation.vue";
</script>

<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<style scoped>

</style>
<style>
/* 加一段非 scoped 全局样式来撑满页面 */
html, body,  #app{
  width: 100%;
  height: 100%;
  padding: 0;
  margin: 0;
}

</style>
