import React, { Component } from 'react'
import {Col, PanelGroup, Panel} from 'react-bootstrap'
import PlanService from '../services/PlanService'
import ActiveInactiveBtn from '../components/ActiveInactiveBtn'

import '../App.css';

class ListPlansPage extends Component {
  constructor(props) {
    super(props)
    this.setActiveInactive = this.setActiveInactive.bind(this)

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
    const editedPlanIdx = newPlans.findIndex((plan) => {return plan.id === event.id})
    newPlans[editedPlanIdx].active = event.status
    this.setState({
      ...this.state,
      plans: newPlans
    })
  }

  render() {
    if (this.state.plans == null) {
      return null
    }
    return (
      <div className="App">
        <div className="App-intro">
          <Col xs={12}>
          <h3>Plans</h3>

          {this.state.plans.map((plan, idx) => {
            const planName = plan.source + " - " + plan.version
            return (
              <PanelGroup accordion key={idx}>
                <Panel header={planName} eventKey={idx}>
                  <span className="rules-header">Completed: </span>{plan.completed.toString()}
                  <br/>
                  <span className="rules-header">Active: </span>{plan.active.toString()}
                  <ActiveInactiveBtn planId={plan.id} currentStatus={plan.active} onChange={this.setActiveInactive}/>
                  <br />
                  <span className="rules-header">Rules: </span>
                  <div className="rules">
                    <div className="rule-defs">
                      {plan.rules.map((rule, ruleIdx) => {
                        return <div key={ruleIdx}>{rule.eventName} - {rule.type} - {rule.condition}: {rule.conditionValue}</div>
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
