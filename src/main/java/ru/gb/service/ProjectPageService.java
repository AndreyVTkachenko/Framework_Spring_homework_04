package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.model.Project;
import ru.gb.model.ProjectPageDto;
import ru.gb.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectPageService {

    private final ProjectRepository projectRepository;

    public List<ProjectPageDto> getAll() {
        return projectRepository.getAll().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public Optional<ProjectPageDto> getById(Long id) {
        return projectRepository.getById(id)
                .map(this::convert);
    }

    private ProjectPageDto convert(Project project) {
        ProjectPageDto dto = new ProjectPageDto();
        dto.setId(String.valueOf(project.getId()));
        dto.setName(project.getName());
        return dto;
    }
}
