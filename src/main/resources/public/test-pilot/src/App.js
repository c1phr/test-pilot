import React, { Component } from 'react';
import PlanService from './services/PlanService'

import './App.css';

class App extends Component {
  constructor(props) {
    super(props)
    this.componentWillMount.bind(this)
    this.state = {}
  }

  componentWillMount() {
    PlanService.getPlans().then((plans) => {
      this.setState({
        ...this.state,
        plans: plans
      })
    })
  }

  render() {
    if (this.state.plans == null) {
      return null
    }
    return (
        <p className="App-intro">
          <h3>Plans</h3>

          {this.state.plans.data.map((plan) => {
            return (
              <div className="plan">
                <span className="rules-header">Source: </span>{plan.source}
                <br/>
                <span className="rules-header">Version: </span>{plan.version}
                <br/>
                <span className="rules-header">Completed: </span>{plan.completed.toString()}
                <br/>
                <span className="rules-header">Active: </span>{plan.active.toString()}
                <br />
                <span className="rules-header">Rules: </span>
                <div className="rules">
                  <div className="rule-defs">
                    {plan.rules.map((rule) => {
                      return <div>{rule.eventName} - {rule.type} - {rule.condition}: {rule.conditionValue}</div>
                    })}
                  </div>
                </div>
              </div>
            )
          })}

        </p>
    );
  }
}

export default App;
