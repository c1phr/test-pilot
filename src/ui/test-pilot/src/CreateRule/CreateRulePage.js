import React, { Component } from 'react'
import PlanService from '../services/PlanService'
import {FormGroup, ControlLabel, FormControl} from 'react-bootstrap'

import '../App.css'

class CreateRulePage extends Component {
  constructor(props) {
    super(props)
    this.onChange = this.onChange.bind(this)
    this.onSubmit = this.onSubmit.bind(this)
    this.goBack = this.goBack.bind(this)
    this.state = {
      ruleType: "OCCURRENCE",
      ruleCondition: "EQUALS"
    }
  }

  componentWillMount() {
    PlanService.getPlan(this.props.params.planId).then((plan) => {
      this.setState({
        ...this.state,
        plan: plan.data
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
    const newRule = {
      eventName: this.state.eventName,
      ruleType: this.state.ruleType,
      ruleCondition: this.state.ruleCondition,
      conditionValue: this.state.conditionValue
    }
    PlanService.addRule(this.state.plan.id, newRule).then((resp) => {
      this.props.router.push('/')
    })
    event.preventDefault()
  }

  render() {
    if (this.state.plan == null) {
      return null
    }

    return (
      <div className="App-body">
        <h2>Add Test Rule - {this.state.plan.source} | {this.state.plan.version}</h2>
        <form onSubmit={this.onSubmit}>
          {this.FieldGroup({id: "input-event-name", label: "Event Name", props: {type: "text", name: "eventName", onChange: this.onChange}})}
          <br/>
          <FormGroup controlId="rule-type">
            <ControlLabel>Rule Type</ControlLabel>
            <FormControl componentClass="select" placeholder="Rule Type" name="ruleType" onChange={this.onChange}>
              <option value="OCCURRENCE">Occurrence</option>
              <option value="PAYLOAD">Payload</option>
            </FormControl>
          </FormGroup>
          <br />
          <FormGroup controlId="rule-condition">
            <ControlLabel>Rule Condition</ControlLabel>
            <FormControl componentClass="select" placeholder="Rule Condition" name="ruleCondition" onChange={this.onChange}>
              <option value="EQUALS">Equals</option>
              <option value="NOT_EQUALS">Does Not Equal</option>
              <option value="GREATER_THAN">Is Greater than</option>
              <option value="LESS_THAN">Is Less Than</option>
              <option value="GREATER_THAN_EQUAL">Is Greater Than or Equal To</option>
              <option value="LESS_THAN_EQUAL">Is Less Than or Equal To</option>
              <option value="CONTAINS">Contains</option>
              <option value="NOT_CONTAINS">Does Not Contain</option>
            </FormControl>
          </FormGroup>
          <br />
          {this.FieldGroup({id: "input-condition-value", label: "Value", props: {type: "text", name: "conditionValue", onChange: this.onChange}})}
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

export default CreateRulePage
