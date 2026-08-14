import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardChartIntervalSelectorComponent } from './dashboard-chart-interval-selector.component';

const meta: Meta<DashboardChartIntervalSelectorComponent> = {
  title: 'Components/DashboardChartIntervalSelectorComponent',
  component: DashboardChartIntervalSelectorComponent,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardChartIntervalSelectorComponent>;

export const Primary: Story = {
  args: {},
};
