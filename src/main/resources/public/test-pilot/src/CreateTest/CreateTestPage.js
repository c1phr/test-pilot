import React, { Component } from 'react'
import PlanService from '../services/PlanService'
import SourceSelector from './components/SourceSelector'
import {FormGroup, ControlLabel, FormControl} from 'react-bootstrap'

import '../App.css'

class CreateTestPage extends Component {
  constructor(props) {
    super(props)
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

  FieldGroup({ id, label, props }) {
    return (
      <FormGroup controlId={id}>
        <ControlLabel>{label}</ControlLabel>
        <FormControl {...props} />
      </FormGroup>
    );
  }

  onChange(event, {value, method}) {
    const target = event.target
    const value = target.value
    const name = target.name
    const payload = {
      name,
      value
    }
    this.setState({
      ...this.state,
      name: value
    })
  }

  render() {
    if (this.state.plans == null) {
      return null
    }
    var planSources = this.state.plans.map((plan) => {return plan.source})
    planSources = Array.from(new Set(planSources))
    return (
      <div className="App-body">
        <h2>Add Test Plan</h2>
        <form method="POST" action="/api/plan">
          <label htmlFor="source">Event Source</label>
          <SourceSelector sources={planSources} onChange={this.onChange} />
          <br/>
          {this.FieldGroup({id: "input-version", label: "Version String", props: {type: "text"}})}
          <br/>
          <FormGroup controlId="notification-type">
            <ControlLabel>Notification Type</ControlLabel>
            <FormControl componentClass="select" placeholder="Notification Type">
              <option value="BAMBOO">Bamboo</option>
              <option value="EMAIL">Email</option>
            </FormControl>
          </FormGroup>
          <br />
          {this.FieldGroup({id: "input-notification-target", label: "Notification Target", props: {type: "text"}})}
          <button type="submit" className="btn btn-default">
            Submit
          </button>
        </form>
      </div>
    )
  }
}

export default CreateTestPage
