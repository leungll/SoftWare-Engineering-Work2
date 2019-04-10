<template>
  <div id="app" class="note" :style="note">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      note: {
        backgroundImage: "url(" + require("@/assets/1.jpg") + ")",
        backgroundRepeat: "no-repeat",
        // backgroundSize: "100% auto",
        marginTop: "5px",
      },
    }
  },
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 60px; */
  border: 1px solid blue;
  width: 100%;
  /* opacity: 0.7; */
}
html,body,#app {
  height: 98.7%;
}
</style>
