import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';

import TopBanner from '../components/TopBanner';
import HeaderNav from '../components/HeaderNav';
import ImageSlider from '../components/ImageSlider';
import Messages from '../components/Messages';
import SlotGameItems from '../components/SlotGameItems';
import Footer from '../components/Footer';

class SlotItemsContainer extends Component {

  constructor(props) {
    super(props);
    this.images = sliders;
  }

  render() {
    return (
      <div>
        <SlotGameItems {...this.props}/>
        <Footer />
      </div>
    );
  }
}

function mapStateToProps(state) {
  const {user, messages, slot} = state;
  return {user, messages, slot};
}

export default connect(mapStateToProps)(SlotItemsContainer);