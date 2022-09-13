<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top:20px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" style="width: 100%;">
                        <h5 class="card-title text-center" style="margin-top:10px;"> {{$store.state.user.username}} </h5>
                        <p class="card-text text-center">河北大学网络空间安全与计算机学院</p>
                        <p class="card-text text-center">计算机科学与技术专业</p>
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <span style="font-size:130%;">我的Bot</span>
                        <button type="button" class="btn btn-success float-end" data-bs-toggle="modal" data-bs-target="#add-bot-btn">
                            创建Bot
                        </button>
                       <!-- Modal -->
                        <div class="modal fade" id="add-bot-btn" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">创建Bot</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    ...
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary">创建</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                        <thead>
                            <tr>
                                <th> 名称 </th>
                                <th> 创建时间 </th>
                                <th> 操作 </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="bot in bots" :key="bot.id">
                                <td> {{bot.title}} </td>
                                <td>  {{bot.createtime}} </td>
                                <td>
                                    <button type="button" class="btn btn-primary" style="margin-right:10px;">修改</button>
                                    <button type="button" class="btn btn-danger">删除</button>
                                </td>
                            </tr>
                        </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';
import $ from 'jquery';
import { useStore } from 'vuex';

export default{
    name: "UserBotIndexView",
    components: {  },
    setup() {
        const store = useStore();
        let bots = ref([]);
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
        refresh_bots();
        return {
            bots,
        }
    }
}
</script>

<style scoped>

</style>