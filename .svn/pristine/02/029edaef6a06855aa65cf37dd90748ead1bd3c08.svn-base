import React, {Component, PropTypes} from 'react';
import Marquee from './Marquee';

import {loadMessages} from '../actions/MessageAction';
import {redirect, absUrl} from '../utils/url';

class Messages extends Component {

  constructor(props) {
    super(props);
    this.handleMessageClick = this.handleMessageClick.bind(this);
  }

  componentDidMount() {
    const {dispatch} = this.props;
    dispatch(loadMessages());
  }

  handleMessageClick() {
    redirect('/m/main?code=new_list');
  }

  render() {
    const {messages} = this.props;
    let messageText = [];
    messages.map(message => {
      messageText.push(message['title']);
    });
    let text = messageText.join('   ');
    return (
      <div className="message-marquee" onClick={this.handleMessageClick}>
        <Marquee text={text} hoverToStop={true} loop={true} className=""/>
      </div>
    );
  }
}

Messages.propTypes = {
  messages: PropTypes.array.isRequired
};

export default Messages;
