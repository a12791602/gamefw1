import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';

import TopBanner from '../components/TopBanner';
import HeaderNav from '../components/HeaderNav';
import ImageSlider from '../components/ImageSlider';
import Messages from '../components/Messages';
import LiveTypes from '../components/LiveTypes';
import Footer from '../components/Footer';

class LiveContainer extends Component {

  constructor(props) {
    super(props);
    this.images = sliders;
  }

  render() {
    return (
      <div className="page slot-page">
        <TopBanner {...this.props} />
        <HeaderNav />
        <ImageSlider images={this.images}/>
        <Messages {...this.props}/>

        <LiveTypes {...this.props}/>

        <Footer />
      </div>
    );
  }
}

function mapStateToProps(state) {
  const {user, messages, live} = state;
  return {user, messages, live};
}

export default connect(mapStateToProps)(LiveContainer);