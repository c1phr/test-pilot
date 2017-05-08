import React, { Component } from 'react'
import {Button} from 'react-bootstrap'
import PlanService from '../services/PlanService'
import PropTypes from 'prop-types'

class ActiveInactiveBtn extends Component {
  constructor(props) {
    super(props)
    this.changeStatus = this.changeStatus.bind(this)
  }

  changeStatus() {
    PlanService.setPlanStatus(this.props.planId, !this.props.currentStatus).then(() => {
      const event = {
        status: !this.props.currentStatus,
        id: this.props.planId
      }
      this.props.onChange(event)
    })

  }

  render() {
    const btnText = this.props.currentStatus ? "Deactivate" : "Activate"

    return (
      <Button bsStyle="primary" onClick={this.changeStatus}>{btnText}</Button>
    )
  }
}

ActiveInactiveBtn.propTypes = {
  planId: PropTypes.string.isRequired,
  currentStatus: PropTypes.bool.isRequired,
  onChange: PropTypes.func
}

export default ActiveInactiveBtn