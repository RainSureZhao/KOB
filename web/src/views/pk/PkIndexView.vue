<template>
<!--    <ContentField>-->
<!--        对战-->
<!--    </ContentField>-->
    <PlayGround v-if="$store.state.pk.status === 'playing'">
        
    </PlayGround>

    <MatchGround v-if="$store.state.pk.status === 'matching'">

    </MatchGround>
</template>

<script>

import PlayGround from "@/components/PlayGround.vue";
import MatchGround from "@/components/MatchGround";
import { onMounted } from "vue";
import { onUnmounted } from "vue";
import { useStore } from "vuex";
export default{
    name: "PkIndexView",
    components: {MatchGround,  PlayGround },
    setup() {
        const store = useStore();
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
                const data = JSON.parse(message.data);
                if(data.event === "start-matching") {  // 匹配成功
                  store.commit("updateOpponent", {
                    username: data.opponent_username,
                    photo: data.opponent_photo,
                  });
                  setTimeout(()=> {
                    store.commit("updateStatus", "playing");
                  }, 1500);
                  store.commit("updateGamemap", data.gamemap);
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