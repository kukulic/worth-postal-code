import React from 'react';
import './App.css';
import {Row, Col, Container} from 'react-bootstrap';
import {FullPostcode, Outcode} from "./view";

function App() {
    return (
        <div>
            <div className="header-title-large space-top-large">
                <span>Administration screen for England postal offices</span>
            </div>
            <Container>
                <Row>
                    <Col>
                        <Outcode/>
                    </Col>
                    <Col>
                        <FullPostcode/>
                    </Col>
                </Row>
            </Container>
        </div>
    );
}

export default App;
