<template>
<!--    <ContentField>-->
<!--        对战-->
<!--    </ContentField>-->
    <PlayGround v-if="$store.state.pk.status === 'playing'">
        
    </PlayGround>

    <MatchGround v-if="$store.state.pk.status === 'matching'">

    </MatchGround>

    <ResultBoard v-if="$store.state.pk.loser !== 'none'" >

    </ResultBoard>
</template>

<script>

import PlayGround from "@/components/PlayGround.vue";
import MatchGround from "@/components/MatchGround";
import { onMounted } from "vue";
import { onUnmounted } from "vue";
import { useStore } from "vuex";
import ResultBoard from "@/components/ResultBoard";
export default{
    name: "PkIndexView",
    components: {MatchGround,  PlayGround, ResultBoard },
    setup() {
        const store = useStore();
        store.commit("updateLoser", "none");
        store.commit("updateIsRecord", false);
        store.commit("updateOpponent", {
          username: "First",
          photo: "https://cdn.acwing.com/media/user/profile/photo/51543_lg_4c1d0f5a55.jpg",
        });
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}/`;
        let socket = null;
        onMounted(() => {
            socket = new WebSocket(socketUrl);
            socket.onopen = () => {
                console.log("connected!");
                store.commit("updateSocket", socket);
            }
            socket.onmessage = (message) => {
                // console.log("test get message!!");
                const data = JSON.parse(message.data);
                if(data.event === "start-matching") {  // 匹配成功
                  store.commit("updateOpponent", {
                    username: data.opponent_username,
                    photo: data.opponent_photo,
                  });
                  setTimeout(()=> {
                    store.commit("updateStatus", "playing");
                  }, 500);
                  store.commit("updateGame", data.game);
                }else if(data.event === "move") {
                    // console.log("test move!!!");
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    snake0.set_direction(data.a_direction);
                    snake1.set_direction(data.b_direction);
                }else if(data.event === "result") {
                    console.log(data);
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    if(data.loser === "all" || data.loser === "A") {
                      snake0.status = "die";
                    }
                    if(data.loser === "all" || data.loser === "B") {
                      snake1.status = "die";
                    }
                    store.commit("updateLoser", data.loser);
                }
            }
            socket.onclose = () => {
                console.log("disconnected!");
            }
        });
        onUnmounted(() => {
            socket.close();
            store.commit("updateStatus", "matching");
        });
    }
}
</script>

<style scoped>

</style>