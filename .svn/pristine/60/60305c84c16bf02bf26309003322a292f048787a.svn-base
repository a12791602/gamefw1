import React, {PropTypes, Component} from 'react';

import {Link} from 'react-router';
import {absUrl} from '../utils/url';

class SlotTypes extends Component {

  constructor(props) {
    super(props);
    this.slots = [{
      id: 'ag',
      title: 'AG电玩',
      url: '/m/main?code=slot_ag',
    }, {
      id: 'mg',
      title: 'MG电玩',
    }, {
      id: 'pt',
      title: 'PT电玩',
    }, {
      id: 'os',
      title: 'OS电玩',
    }, {
      id: 'ttg',
      title: 'TTG电玩',
    }, {
      id: 'nt',
      title: 'NT电玩',
    }];
  }

  slotLink(slot) {
    if (slot.url == undefined) {
      return <Link to={ `/slot/${slot.id}` }> {slot.title}  </Link>;
    }
    return <a href={ absUrl(slot.url) }> {slot.title}</a>;
  }

  render() {
    return (
      <div className="slot-type-links">
        <ul className="clearfix items">
          {this.slots.map((slot, index) => {
            return (
              <li key={index} className={"slot-item-" + slot.id}>
                <div className="slot-link-inner">{ this.slotLink(slot) }</div>
              </li>);
          })}
        </ul>
      </div>
    );
  }
};

export default SlotTypes;