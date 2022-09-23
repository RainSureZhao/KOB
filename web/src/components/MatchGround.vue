<template>
  <div class="matchground">
    <div class="row">
      <div class="col-5">
        <div class="user-photo">
          <img :src="$store.state.user.photo" alt="">
        </div>
        <div class="user-username">
          {{ $store.state.user.username }}
        </div>
      </div>
      <div class="col-2">
          <div class="vs-photo">
            <img src="@/assets/images/VS.webp" alt="">
          </div>
        <div class="user-select-bot">
          <select v-model="select_bot" class="form-select" aria-label="Default select example">
            <option selected value="-1"> 亲自出马 </option>
            <option v-for="bot in bots" :key="bot.id"  :value="bot.id">
              {{ bot.title }}
            </option>
          </select>
        </div>
      </div>
      <div class="col-5">
        <div class="user-photo">
          <img :src="$store.state.pk.opponent_photo" alt="">
        </div>
        <div class="user-username">
          {{ $store.state.pk.opponent_username }}
        </div>
      </div>
      <div class="col-12 start-match">
        <button type="button" class="btn btn-success btn-lg" @click="click_match_btn">{{ match_btn_info }}</button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import {useStore} from "vuex";
import $ from 'jquery'
export default {
  name: "MatchGround",
  setup() {
    const store = useStore();
    let match_btn_info = ref("开始匹配");
    let bots = ref([]);
    let select_bot = ref("-1");
    const refresh_bots = () => {
      $.ajax({
        url : "http://127.0.0.1:3000/user/bot/getlist/",
        type: "get",
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        success(resp) {
          bots.value = resp;
        }
      })
    };

    const click_match_btn = () => {
      if(match_btn_info.value === "开始匹配") {
        match_btn_info.value = "取消";
        store.state.pk.socket.send(JSON.stringify({
          event: "start-matching",
          bot_id: select_bot.value,
        }));
      }else {
        match_btn_info.value = "开始匹配";
        store.state.pk.socket.send(JSON.stringify({
          event: "stop-matching",
        }));
      }
    }
    refresh_bots();
    return {
      match_btn_info,
      click_match_btn,
      bots,
      select_bot,
    }
  }
}
</script>

<style scoped>
div.matchground {
  width: 60vw;
  height: 70vh;
  margin: 40px auto;
  /*background-color: rgb(50, 50, 50, 0.5);*/
  background-image: url("@/assets/images/match-background.webp");
}
div.user-photo {
  text-align: center;
  padding-top: 10vh;
}
div.user-photo > img {
  border-radius: 50%;
  width: 20vh;
}
div.user-username {
  text-align: center;
  font-size: 24px;
  font-weight: 600;
  color: white;
  padding-top: 2vh;
}
div.start-match {
  text-align: center;
  margin-top: 80px;
}
div.vs-photo {
  padding-top: 16vh;
  text-align: center;
}
div.vs-photo > img {
  width: 120px;
}
div.user-select-bot {
  margin-top: 20px;
}
</style>