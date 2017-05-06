import React, { Component } from 'react'
import Autosuggest from 'react-autosuggest'
import escapeRegex from '../../components/EscapeRegex'
import PropTypes from 'prop-types'

import '../../components/AutosuggestStyle.css'

class SourceSelector extends Component {
  constructor(props) {
    super(props)
    this.onChange = this.onChange.bind(this)
    this.onSuggestionsFetchRequested = this.onSuggestionsFetchRequested.bind(this)
    this.onSuggestionsClearRequested = this.onSuggestionsClearRequested.bind(this)
    this.getSuggestions = this.getSuggestions.bind(this)
    this.state = {
      value: '',
      suggestions: []
    }
  }

  onChange(event, {newValue, method}) {
    this.setState({
      value: newValue
    })
  }

  onSuggestionsFetchRequested({value}) {
    this.setState({
      suggestions: this.getSuggestions(value)
    })
  }

  onSuggestionsClearRequested() {
    this.setState({
      suggestions: []
    })
  }

  getSuggestions(value) {
    const escaped = escapeRegex(value.trim())
    const regex = new RegExp('^' + escaped, 'i')
    return this.props.sources.filter(source => regex.test(source))
  }

  getSuggestionValue(value) {
    return value
  }

  renderSuggestion(suggestion) {
    return (
      <span>{suggestion}</span>
    )
  }

  render() {
    if (this.props.sources == null) {
      return null
    }

    const inputProps = {
      placeholder: "Source",
      value: this.state.value,
      onChange: this.onChange
    }
    return (
      <Autosuggest
        suggestions={this.state.suggestions}
        onSuggestionsFetchRequested={this.onSuggestionsFetchRequested}
        onSuggestionsClearRequested={this.onSuggestionsClearRequested}
        getSuggestionValue={this.getSuggestionValue}
        renderSuggestion={this.renderSuggestion}
        shouldRenderSuggestions={() => {return true}}
        inputProps={inputProps}
      />
    )
  }
}

SourceSelector.propTypes = {
  sources: PropTypes.array.isRequired
}

SourceSelector.defaultProps = {
  sources: []
}

export default SourceSelector
