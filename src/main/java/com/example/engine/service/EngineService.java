package com.example.engine.service;

import com.example.engine.exception.AlreadyExistsException;
import com.example.engine.exception.NotFoundException;
import com.example.engine.model.Engine;
import com.example.engine.model.Petrol;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/engines")
@SwaggerDefinition(
        host = "http://localhost:8080",
        consumes = {"application/xml", "application/json"})
public class EngineService {

    private static final Logger logger = LoggerFactory.getLogger(EngineService.class);

    private List<Engine> engines;

    EngineService() {
        engines = new ArrayList<>();
        engines.add(new Engine(1, "D8", 300, Petrol.DIESEL));
        engines.add(new Engine(2, "D6", 250, Petrol.DIESEL));
        engines.add(new Engine(3, "T8", 325));
        engines.add(new Engine(4, "T6", 275));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation("Get all engines")

    public List<Engine> getEngines(
            @ApiParam(value = "Search for engine name")
            @RequestParam(value = "name", required = false) String name,
            @ApiParam(type = "param", value = "Search for engines with a minimum power", required = false, example = "300")
            @RequestParam(value = "minimumPower", required = false) Integer minimumPower
    ) {
        if (name != null && !name.isEmpty()) {
            return engines.stream().filter(engine -> engine.getName().equals(name)).collect(Collectors.toList());
        }
        if (minimumPower != null) {
            return engines.stream().filter(engine -> engine.getPower() >= minimumPower).collect(Collectors.toList());
        }
        return engines;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get engine by id", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Found Engine"),
            @ApiResponse(code = 404, message = "Engine not found")
    })
    public Engine getEngine(@ApiParam("Engine identifier") @PathVariable("id") int id) throws NotFoundException {
        Engine engine = engines.stream().filter(e -> e.getId() == id).findFirst().orElse(null);

        if (engine == null) {
            throw new NotFoundException();
        }
        return engine;
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "Create a new Engine", produces = "application/json", code = 200)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Created"),
            @ApiResponse(code = 409, message = "Engine already exists")
    })
    public Engine create(@RequestBody Engine engine) throws AlreadyExistsException {
        if (!engines.contains(engine)) {
            engines.add(engine);
        } else {
            throw new AlreadyExistsException(engine.getId());
        }

        return engine;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update an engine.", response = Engine.class, produces = "application/json", code = 200)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Updated and return the object", response = Engine.class),
            @ApiResponse(code = 404, message = "Engine not found"),
    })
    public Engine update(@ApiParam(value = "identifier of an engine") @PathVariable("id") int id,
                         @ApiParam(value = "The Engine object") @RequestBody Engine engine)
            throws NotFoundException {

        if (engines.contains(engine)) {
            Engine e = engines.get(engines.indexOf(engine));
            e.setName(engine.getName());
            e.setPetrol(engine.getPetrol());
            e.setPower(engine.getPower());
        } else {
            throw new NotFoundException();
        }

        return engine;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete an engine", consumes = "application/json", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Engine is deleted"),
            @ApiResponse(code = 404, message = "Engine is not found")
    })
    public ResponseEntity delete(@ApiParam("identifier of an engine") @PathVariable("id") int id) throws NotFoundException {
        logger.info("Delete engine "+ id);

        Engine engine = engines.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if (engine != null) {
            engines.remove(engine);
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            throw new NotFoundException();
        }


    }
}
