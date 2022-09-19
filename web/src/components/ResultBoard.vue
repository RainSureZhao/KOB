<template>
  <div class="result-board">
    <div class="result-board-text" v-if="$store.state.pk.loser === 'all'">
      平局！
    </div>
    <div class="result-board-text" v-else-if="$store.state.pk.loser === 'A' && $store.state.pk.a_id === parseInt($store.state.user.id)">
      失败！
    </div>
    <div class="result-board-text" v-else-if="$store.state.pk.loser === 'B' && $store.state.pk.b_id === parseInt($store.state.user.id)">
      失败！
    </div>
    <div class="result-board-text" v-else>
      胜利！
    </div>
    <div class="result-board-btn">
      <button @click="restart" type="button" class="btn btn-primary">
        再来!
      </button>
    </div>
  </div>
</template>

<script>
import {useStore} from 'vuex'

export default {
  name: "ResultBoard",

  setup() {
    const store = useStore();
    const restart = () => {
      store.commit("updateStatus", "matching");
      store.commit("updateLoser", "none");
      store.commit("updateOpponent", {
        username: "First",
        photo: "https://cdn.acwing.com/media/user/profile/photo/51543_lg_4c1d0f5a55.jpg",
      });
    }
    return {
      restart
    }

  }
}
</script>

<style scoped>
.result-board {
  height: 30vh;
  width: 30vw;
  background-color: rgba(50, 50, 50, 0.5);
  position: absolute;
  top: 30vh;
  left: 35vw;
}

.result-board-text {
  text-align: center;
  color: white;
  font-size: 50px;
  font-weight: 600;
  font-style: italic;
  padding-top: 5vh;
}

.result-board-btn {
  text-align: center;
  padding-top: 7vh;
}
</style>