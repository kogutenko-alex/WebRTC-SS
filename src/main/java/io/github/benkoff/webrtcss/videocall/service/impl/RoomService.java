package io.github.benkoff.webrtcss.videocall.service.impl;

import io.github.benkoff.webrtcss.videocall.service.bean.Room;
import io.github.benkoff.webrtcss.videocall.service.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public class RoomService {    
    private final Parser parser;
    private final Set<Room> rooms = new TreeSet<>(Comparator.comparing(Room::getId));

    @Autowired
    public RoomService(Parser parser) {
        this.parser = parser;
    }

    public Set<Room> getRooms() {
        TreeSet<Room> defensiveCopy = new TreeSet<>(Comparator.comparing(Room::getId));
        defensiveCopy.addAll(rooms);

        return defensiveCopy;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Optional<Room> findRoomByStringId(final String sid) {
        // simple get() because of parser errors handling
        return rooms.stream().filter(r -> r.getId().equals(parser.parseId(sid).get())).findAny();
    }

    public Long getRoomId(Room room) {
        return room.getId();
    }

    public Map<String, WebSocketSession> getClients(Room room) {
        return Optional.ofNullable(room)
                .map(r -> Collections.unmodifiableMap(r.getClients()))
                .orElse(Collections.emptyMap());
    }

    public void addClient(Room room, String name, WebSocketSession session) {
        room.getClients().put(name, session);
    }

    public void removeClientByName(Room room, String name) {
        room.getClients().remove(name);
    }
}
