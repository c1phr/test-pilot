
import axios from 'axios'
import {PLAN_API} from '../ApiConstants'

export default class PlanService {
  static getPlans() {
    return axios.get(PLAN_API)
  }

  static submitPlan(newPlan) {
    return axios.post(PLAN_API, newPlan)
  }
}