import axios from 'axios'

axios.defaults.baseURL = 'http://localhost:7001'
axios.defaults.withCredentials = true
export const $axios = axios
