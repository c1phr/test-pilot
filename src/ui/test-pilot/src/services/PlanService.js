
import axios from 'axios'
import {PLAN_API} from '../ApiConstants'

export default class PlanService {
  static getPlans() {
    return axios.get(PLAN_API)
  }

  static getPlan(planId) {
    return axios.get(PLAN_API + '/' + planId)
  }

  static submitPlan(newPlan) {
    return axios.post(PLAN_API, newPlan)
  }

  static setPlanStatus(planId, newStatus) {
    const payload = {
      activeStatus: newStatus
    }
    return axios.post(PLAN_API + '/' + planId + '/active', payload)
  }

  static deleteRule(planId, ruleId) {
    return axios.delete(this._getRuleApi(planId) + "/" + ruleId)
  }

  static addRule(planId, rulePayload) {
    return axios.post(this._getRuleApi(planId), rulePayload)
  }

  static _getRuleApi(planId) {
    return PLAN_API + '/' + planId + '/rule'
  }
}