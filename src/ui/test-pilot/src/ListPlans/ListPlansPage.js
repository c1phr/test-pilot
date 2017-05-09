import React, { Component } from 'react'
import {Col, PanelGroup, Panel, Button} from 'react-bootstrap'
import PlanService from '../services/PlanService'
import ActiveInactiveBtn from '../components/ActiveInactiveBtn'

import '../App.css';

class ListPlansPage extends Component {
  constructor(props) {
    super(props)
    this.setActiveInactive = this.setActiveInactive.bind(this)
    this.deleteRule = this.deleteRule.bind(this)
    this.state = {}
  }

  componentWillMount() {
    PlanService.getPlans().then((plans) => {
      this.setState({
        ...this.state,
        plans: plans.data
      })
    })
  }

  setActiveInactive(event) {
    const newPlans = this.state.plans
    const editedPlanIdx = newPlans.findIndex((planData) => {return planData.plan.id === event.id})
    newPlans[editedPlanIdx].plan.active = event.status
    this.setState({
      ...this.state,
      plans: newPlans
    })
  }

  deleteRule(planId, ruleId) {
    PlanService.deleteRule(planId, ruleId).then((resp) => {
      const newPlans = this.state.plans
      const editedPlanIdx = newPlans.findIndex((plan) => plan.id === resp.data.id)
      newPlans[editedPlanIdx].rules = resp.data.rules
      this.setState({
        ...this.state,
        plans: newPlans
      })
    })
  }

  render() {
    if (this.state.plans == null) {
      return null
    }
    return (
      <div className="App">
        <div className="App-body">
          <Col xs={12}>
          <h3>Plans</h3>

          {this.state.plans.map((planData, idx) => {
            const planName = planData.plan.source + " - " + planData.plan.version
            return (
              <PanelGroup accordion key={idx}>
                <Panel header={planName} eventKey={idx}>
                  <span className="rules-header">Completed: </span>{planData.plan.completed.toString()}
                  <br/>
                  <span className="rules-header">Active: </span>{planData.plan.active.toString()}
                  <ActiveInactiveBtn planId={planData.plan.id} currentStatus={planData.plan.active} onChange={this.setActiveInactive}/>
                  <br />
                  <span className="rules-header">Rules: </span>
                  <Button bsStyle="default" onClick={() => this.props.router.push('/' + planData.plan.id + '/addRule')}>Add Rules</Button>
                  <div className="rules">
                    <div className="rule-defs">
                      {planData.plan.rules.map((rule, ruleIdx) => {
                        return <div key={ruleIdx}>
                          <Button bsStyle="danger" onClick={() => this.deleteRule(planData.plan.id, rule.id)}>Delete</Button>
                          {rule.eventName} - {rule.type} - {rule.condition}: {rule.conditionValue} | Found: {planData.events[rule.eventName]}
                          </div>
                      })}
                    </div>
                  </div>
                </Panel>
              </PanelGroup>
            )
          })}
          </Col>
        </div>
      </div>
    );
  }
}

export default ListPlansPage;
