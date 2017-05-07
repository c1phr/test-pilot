import React, { Component } from 'react'
import PlanService from '../services/PlanService'
import SourceSelector from './components/SourceSelector'
import {FormGroup, ControlLabel, FormControl} from 'react-bootstrap'

import '../App.css'

class CreateTestPage extends Component {
  constructor(props) {
    super(props)
    this.onChange = this.onChange.bind(this)
    this.onSubmit = this.onSubmit.bind(this)
    this.goBack = this.goBack.bind(this)
    this.state = {
      notificationType: "BAMBOO"
    }
  }

  componentWillMount() {
    PlanService.getPlans().then((plans) => {
      this.setState({
        ...this.state,
        plans: plans.data
      })
    })
  }

  goBack(event) {
    this.props.router.push('/')
  }

  FieldGroup({ id, label, props }) {
    return (
      <FormGroup controlId={id}>
        <ControlLabel>{label}</ControlLabel>
        <FormControl {...props} />
      </FormGroup>
    );
  }

  onChange(event) {
    const target = event.target
    const value = target.value
    const name = target.name
    this.setState({      
      [name]: value
    })
  }

  onSubmit(event) {
    const newPlan = {
      source: this.state.source,
      version: this.state.version,
      notificationType: this.state.notificationType,
      notificationTarget: this.state.notificationTarget
    }
    PlanService.submitPlan(newPlan).then((resp) => {
      this.props.router.push('/')
    })
    event.preventDefault()
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
        <form onSubmit={this.onSubmit}>
          <label htmlFor="source">Event Source</label>
          <SourceSelector sources={planSources} onChange={this.onChange} name="source" />
          <br/>
          {this.FieldGroup({id: "input-version", label: "Version String", props: {type: "text", name: "version", onChange: this.onChange}})}
          <br/>
          <FormGroup controlId="notification-type">
            <ControlLabel>Notification Type</ControlLabel>
            <FormControl componentClass="select" placeholder="Notification Type" name="notificationType" onChange={this.onChange}>
              <option value="BAMBOO">Bamboo</option>
              <option value="EMAIL">Email</option>
            </FormControl>
          </FormGroup>
          <br />
          {this.FieldGroup({id: "input-notification-target", label: "Notification Target", props: {type: "text", name: "notificationTarget", onChange: this.onChange}})}
          <button type="button" className="btn btn-default" onClick={this.goBack}>
            Go Back
          </button>
          <button type="submit" className="btn btn-success">
            Submit
          </button>
        </form>
      </div>
    )
  }
}

export default CreateTestPage
