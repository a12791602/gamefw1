import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';

import TopBanner from '../components/TopBanner';
import HeaderNav from '../components/HeaderNav';
import ImageSlider from '../components/ImageSlider';
import Messages from '../components/Messages';
import SlotTypes from '../components/SlotTypes';
import Footer from '../components/Footer';

class SlotContainer extends Component {

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

        <SlotTypes />

        <Footer />
      </div>
    );
  }
}

function mapStateToProps(state) {
  const {user, messages} = state;
  return {user, messages};
}

export default connect(mapStateToProps)(SlotContainer);