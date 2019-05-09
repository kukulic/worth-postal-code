import React, {Component} from 'react';
import {Row, Col, Container, Button, Form} from 'react-bootstrap';
import Dropdown from 'react-dropdown'
import 'react-dropdown/style.css'
import '../css/main.css';
import {api} from '../service';

export class Outcode extends Component {
    state = {
        outcode: undefined,
        outcodeFirst: undefined,
        outcodeSecond: undefined,
        dropdownGeneral: undefined,
        dropdownFirst: undefined,
        dropdownSecond: undefined,
        distance: undefined,
        outcodeDropdown: undefined,
        loading: true,
        updating: false,
        form: undefined
    };

    componentDidMount() {
        this.getPostal();
    }

    getPostal = () => {
        this.setState({loading: true}, async () => {
            try {
                this.setState({
                    outcodeDropdown: (await api.outcode.getAllOutcodes()).data
                });
            } catch (e) {
                this.setState({error: 'Data not available, try again later'});
            } finally {
                this.setState({loading: false, updating: false});
            }
        });
    };

    onSelect = async (event) => {
        try {
            this.setState({
                outcode: (await api.outcode.getOutcode(event.value)).data
            });
        } catch (e) {
            this.setState({error: 'Data not available, try again later'});
        } finally {
            this.setState({loading: false, updating: false, dropdownGeneral: event.value});
        }
    };

    onSelectFirst = async (event) => {
        try {
            this.setState({
                outcodeFirst: (await api.outcode.getOutcode(event.value)).data
            });
        } catch (e) {
            this.setState({error: 'Data not available, try again later'});
        } finally {
            this.setState({loading: false, updating: false, dropdownFirst: event.value});
        }
    };

    onSelectSecond = async (event) => {
        try {
            this.setState({
                outcodeSecond: (await api.outcode.getOutcode(event.value)).data
            });
        } catch (e) {
            this.setState({error: 'Data not available, try again later'});
        } finally {
            this.setState({loading: false, updating: false, dropdownSecond: event.value});
        }
    };

    updatePostOffice = async (event) => {
        event.preventDefault();
        const {outcode} = this.state
        try {
            await api.outcode.updateOutcode(outcode)
        } catch (e) {
            this.setState({error: 'Error happened while updating postal office'});
        } finally {
            this.setState({outcode: undefined});
            this.getPostal();
        }
    };

    deletePostOffice = async (id) => {
        try {
            await api.outcode.deleteOutcode(id);
            this.setState({outcode: undefined});
            this.getPostal();
        } catch (e) {
            this.setState({error: 'Unexpected error'});
        } finally {
            this.setState({
                loading: false,
                updating: false,
                dropdownGeneral: undefined,
                dropdownFirst: undefined,
                dropdownSecond: undefined
            });
        }
    };

    calculateDistance = async () => {
        const {
            outcodeFirst,
            outcodeSecond
        } = this.state;
        if (outcodeFirst !== undefined && outcodeSecond !== undefined) {
            try {
                this.setState({
                    distance: (await api.outcode.calculateDistance(outcodeFirst.outcode, outcodeSecond.outcode)).data
                });
            } catch (e) {
                this.setState({error: 'Data not available'});
            } finally {
            }
        }
    };

    handleOutcodeChange = (event) => {
        let outcode = Object.assign({}, this.state.outcode);
        outcode.outcode = event.target.value;
        this.setState({outcode: outcode});
    };

    handleLatitudeChange = (event) => {
        let outcode = Object.assign({}, this.state.outcode);
        outcode.latitude = event.target.value;
        this.setState({outcode: outcode});
    };

    handleLongitudeChange = (event) => {
        let outcode = Object.assign({}, this.state.outcode);
        outcode.longitude = event.target.value;
        this.setState({outcode: outcode});
    };

    openUpdateForm() {
        this.setState({updating: true});
    };

    back() {
        this.setState({updating: false});
    }


    render() {
        const {
            outcode,
            outcodeDropdown,
            dropdownGeneral,
            dropdownFirst,
            dropdownSecond,
            distance,
            loading,
            updating
        } = this.state;


        if (loading) {
            return (
                <div>
                    LOADING...
                </div>
            );
        }

        return (
            <Container className="container-small space-top-large">
                <div className="header-title-medium">
                    <span>Postal outcode</span>
                </div>

                <Dropdown className="space-top-large" options={outcodeDropdown} value={dropdownGeneral} onChange={this.onSelect}
                          placeholder="Select an option"/>
                {outcode ?
                    <div className="space-top-small">
                        {updating ?
                            <div>
                                <Form onSubmit={this.updatePostOffice}>
                                    <Form.Group as={Row} controlId="formOutcode">
                                        <Form.Label column sm={4}>Postal code</Form.Label>
                                        <Col sm={8}>
                                            <Form.Control onChange={this.handleOutcodeChange} value={outcode.outcode}/>
                                        </Col>
                                    </Form.Group>

                                    <Form.Group as={Row} controlId="formLatitude">
                                        <Form.Label column sm={4}>Latitude</Form.Label>
                                        <Col sm={8}>
                                            <Form.Control type="number" onChange={this.handleLatitudeChange}
                                                          value={outcode.latitude}/>
                                        </Col>
                                    </Form.Group>

                                    <Form.Group as={Row} controlId="formLongitude">
                                        <Form.Label column sm={4}>Longitude</Form.Label>
                                        <Col sm={8}>
                                            <Form.Control type="number" onChange={this.handleLongitudeChange}
                                                          value={outcode.longitude}/>
                                        </Col>
                                    </Form.Group>

                                    <Button className="btn-space" variant="primary" type="submit">Update</Button>
                                    <Button className="btn-space" variant="secondary"
                                            onClick={() => this.back()}>Back</Button>
                                </Form>

                            </div> :
                            <div>
                                <div>
                                    <Row>
                                        <Col md="4">Postal name:</Col>
                                        <Col>{outcode.outcode}</Col>
                                    </Row>
                                    <Row>
                                        <Col md="4">Latitude:</Col>
                                        <Col>{outcode.latitude}</Col>
                                    </Row>
                                    <Row>
                                        <Col md="4">Longitude:</Col>
                                        <Col>{outcode.longitude}</Col>
                                    </Row>
                                </div>
                                <Button className="space-top-small btn-space" variant="primary"
                                        onClick={() => this.openUpdateForm()}>
                                    Update post office
                                </Button>
                                <Button className="space-top-small btn-space" variant="danger"
                                        onClick={() => this.deletePostOffice(outcode.id)}>
                                    Delete post office
                                </Button>
                            </div>
                        }
                    </div>
                    :
                    <div className="space-top-small">
                        Need to choose one postal office from list to change data
                    </div>
                }
                <div className="space-top-large">
                    <Row>
                        <Col>
                            <Dropdown options={outcodeDropdown} value={dropdownFirst}
                                      onChange={this.onSelectFirst}
                                      placeholder="Select first post code"/>
                        </Col>
                        <Col>
                            <Dropdown options={outcodeDropdown} value={dropdownSecond}
                                      onChange={this.onSelectSecond}
                                      placeholder="Select second post code"/>
                        </Col>
                    </Row>
                </div>
                <Button className="space-top-small" variant="primary" onClick={() => this.calculateDistance()}>
                    Calculate distance
                </Button>
                {(distance != null) ?
                    <div className="space-top-small">
                        Distance between two post offices is {distance} km
                    </div> :
                    <div/>
                }
            </Container>
        );
    }
}
