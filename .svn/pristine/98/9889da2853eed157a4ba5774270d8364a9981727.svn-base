import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';

import BaseComponent from '../components/BaseComponent';

import TopBanner from '../components/TopBanner';
import HeaderNav from '../components/HeaderNav';
import ImageSlider from '../components/ImageSlider';
import Messages from '../components/Messages';
import LiveTypes from '../components/LiveTypes';
import BBIN from '../components/BBIN';
import Footer from '../components/Footer';

class BBINContainer extends BaseComponent {

  constructor(props) {
    super(props);
    this.images = sliders;
  }

  render() {
    return (
      <div className="page promo-page">
        <TopBanner {...this.props} />
        <HeaderNav />
        <ImageSlider images={this.images}/>
        <Messages {...this.props}/>

        <BBIN {...this.props}/>


        <Footer />
      </div>
    );
  }
}

export default connect(state => {
  const {user, messages} = state;
  return {user, messages};
})(BBINContainer);